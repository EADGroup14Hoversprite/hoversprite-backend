package hoversprite.storage.internal.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import hoversprite.storage.external.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public List<String> uploadFiles(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();
        Bucket bucket = StorageClient.getInstance().bucket();

        for (MultipartFile file : files) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            bucket.create(fileName, file.getBytes(), file.getContentType());
            String downloadUrl = "https://storage.googleapis.com/" + bucket.getName() + "/" + fileName;
            urls.add(downloadUrl);
        }
        return urls;
    }

}
