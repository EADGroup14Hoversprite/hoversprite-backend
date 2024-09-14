package hoversprite.storage.external.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {

    List<String> uploadFiles(List<MultipartFile> files) throws IOException;

}
