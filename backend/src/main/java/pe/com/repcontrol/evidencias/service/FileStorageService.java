package pe.com.repcontrol.evidencias.service;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@ApplicationScoped
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService() {
        String path = System.getenv().getOrDefault("EVIDENCE_UPLOAD_PATH", "/tmp/uploads/evidences");
        this.uploadDir = Path.of(path);
        try {
            Files.createDirectories(uploadDir);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de uploads: " + path, e);
        }
    }

    public String store(InputStream data, String extension) throws IOException {
        String uuidName = UUID.randomUUID() + "." + extension;
        Path target = uploadDir.resolve(uuidName);
        Files.copy(data, target, StandardCopyOption.REPLACE_EXISTING);
        return target.toAbsolutePath().toString();
    }

    public void delete(String filePath) {
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (IOException ignored) {
        }
    }

    public Path getUploadDir() {
        return uploadDir;
    }
}
