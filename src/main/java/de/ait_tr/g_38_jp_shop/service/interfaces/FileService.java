package de.ait_tr.g_38_jp_shop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String upload(MultipartFile file, String productTitle);
}
