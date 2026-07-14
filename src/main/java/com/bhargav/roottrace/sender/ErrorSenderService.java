package com.bhargav.roottrace.sender;

import com.bhargav.roottrace.dto.ErrorEventDTO;
import com.bhargav.roottrace.entity.ErrorLog;
import com.bhargav.roottrace.properties.ErrorMonitorProperties;
import com.bhargav.roottrace.service.ErrorLogService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ErrorSenderService {

    private final ErrorMonitorProperties properties;
    private final ErrorLogService service;

    public ErrorSenderService(
            ErrorMonitorProperties properties,
            ErrorLogService service) {

        this.properties = properties;
        this.service = service;
    }

    public void sendError(ErrorEventDTO dto) {

        // Do nothing when RootTrace monitoring is disabled
        if (!properties.isEnable()) {
            return;
        }

        // Safety check
        if (dto == null) {
            System.out.println("RootTrace received null ErrorEventDTO");
            return;
        }

        LocalDateTime now =
                dto.getTimestamp() != null
                        ? dto.getTimestamp()
                        : LocalDateTime.now();

        /*
         * Generate a stable fingerprint for the issue.
         *
         * Same:
         * application
         * environment
         * exception type
         * endpoint
         * application stack frame
         *
         * should produce the same fingerprint.
         */
        String fingerprint = generateFingerprint(dto);

        Optional<ErrorLog> existingError =
                service.getByFingerprint(fingerprint);

        /*
         * SAME ISSUE ALREADY EXISTS
         *
         * Do not create another database row.
         * Increase occurrenceCount and update lastSeen.
         */
        if (existingError.isPresent()) {

            ErrorLog log = existingError.get();

            long currentCount =
                    log.getOccurrenceCount() == null
                            ? 1L
                            : log.getOccurrenceCount();

            log.setOccurrenceCount(currentCount + 1);

            log.setLastSeen(now);

            /*
             * Keep latest event information.
             */
            log.setMessage(dto.getMessage());
            log.setStackTrace(dto.getStackTrace());
            log.setRequestUrl(dto.getEndpoint());
            log.setHttpMethod(dto.getHttpMethod());
            log.setStatusCode(dto.getHttpStatus());

            service.save(log);

            System.out.println(
                    "Grouped RootTrace event"
                            + " | Application: " + safe(dto.getApplicationName())
                            + " | Environment: " + safe(dto.getEnvironment())
                            + " | Fingerprint: " + fingerprint
                            + " | Occurrences: " + log.getOccurrenceCount()
            );

            return;
        }

        /*
         * NEW ISSUE
         *
         * Create a new database row.
         */
        ErrorLog log = new ErrorLog();

        log.setApplicationName(dto.getApplicationName());

        log.setEnvironment(dto.getEnvironment());

        log.setServiceVersion(dto.getServiceVersion());

        log.setExceptionType(dto.getExceptionType());

        log.setMessage(dto.getMessage());

        log.setStackTrace(dto.getStackTrace());

        log.setRequestUrl(dto.getEndpoint());

        log.setHttpMethod(dto.getHttpMethod());

        log.setStatusCode(dto.getHttpStatus());

        /*
         * Temporary severity logic.
         *
         * Later we can implement automatic severity classification.
         */
        log.setSeverity("CRITICAL");

        log.setStatus("OPEN");

        log.setFingerprint(fingerprint);

        log.setOccurrenceCount(1L);

        log.setFirstSeen(now);

        log.setLastSeen(now);

        log.setCreatedAt(now);

        service.save(log);

        System.out.println(
                "Created new RootTrace issue"
                        + " | Application: " + safe(dto.getApplicationName())
                        + " | Environment: " + safe(dto.getEnvironment())
                        + " | Version: " + safe(dto.getServiceVersion())
                        + " | Fingerprint: " + fingerprint
                        + " | Occurrences: 1"
        );
    }


    /*
     * Generate the fingerprint used for grouping identical issues.
     */
    private String generateFingerprint(ErrorEventDTO dto) {

        String fingerprintSource =
                safe(dto.getApplicationName())
                        + "|"
                        + safe(dto.getEnvironment())
                        + "|"
                        + safe(dto.getExceptionType())
                        + "|"
                        + safe(dto.getEndpoint())
                        + "|"
                        + extractApplicationFrame(dto.getStackTrace());

        return sha256(fingerprintSource);
    }


    /*
     * Find the first stack-trace frame belonging to the customer application.
     *
     * Framework/internal Java frames are ignored.
     */
    private String extractApplicationFrame(String stackTrace) {

        if (stackTrace == null || stackTrace.isBlank()) {
            return "unknown-frame";
        }

        String[] lines = stackTrace.split("\\R");

        for (String line : lines) {

            String trimmedLine = line.trim();

            if (trimmedLine.startsWith("at ")
                    && !trimmedLine.startsWith("at java.")
                    && !trimmedLine.startsWith("at jdk.")
                    && !trimmedLine.startsWith("at sun.")
                    && !trimmedLine.startsWith("at jakarta.")
                    && !trimmedLine.startsWith("at org.springframework.")
                    && !trimmedLine.startsWith("at org.apache.")
                    && !trimmedLine.startsWith("at io.roottrace.sdk.")) {

                return trimmedLine;
            }
        }

        return "unknown-frame";
    }


    /*
     * Convert the fingerprint source into a SHA-256 hash.
     */
    private String sha256(String value) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] encodedHash =
                    digest.digest(
                            value.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {

                String hex =
                        Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is not available.",
                    exception
            );
        }
    }


    /*
     * Prevent null values from causing fingerprint-generation problems.
     */
    private String safe(String value) {

        return value == null
                ? ""
                : value;
    }
}