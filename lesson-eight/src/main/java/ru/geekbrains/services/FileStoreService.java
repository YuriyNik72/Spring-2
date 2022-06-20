package ru.geekbrains.services;

import ru.geekbrains.entites.FileMetaDTO;
import ru.geekbrains.repositories.interfaces.IFileMetaProvider;
import ru.geekbrains.repositories.interfaces.IFileSystemProvider;
import ru.geekbrains.services.interfaces.IFileStoreService;
import ru.geekbrains.utils.HashHelper;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
public class FileStoreService implements IFileStoreService {

    @Autowired
    IFileSystemProvider systemProvider;

    @Autowired
    IFileMetaProvider fileMetaProvider;
    private String fileName;

    @Override
    public String storeFile(byte[] content, String fileName, int subFileType) throws IOException, NoSuchAlgorithmException {
        final UUID md5 = HashHelper.getMd5Hash(content);

        String filename = fileMetaProvider.checkFileExists(md5, fileName);
        if (filename == null) {
            fileMetaProvider.saveFileMeta(md5, fileName, subFileType);
            filename = systemProvider.storeFile(content, md5, fileName);
        }

        return filename;
    }

    @Override
    public byte[] getFile(UUID md5) throws IOException {
        String filename = fileMetaProvider.checkFileExists(md5, fileName);
        String ext = FilenameUtils.getExtension(filename);
        String fullFileName = md5.toString() + "." + ext;
        return  systemProvider.getFile(fullFileName);
    }

    @Override
    public Collection<FileMetaDTO> getMetaFiles(int subtype) {
       return fileMetaProvider.getMetaFiles(subtype);
    }

    @Override
    public FileMetaDTO deleteFile(UUID hash, String filename) throws IOException {
        List<String> storedFilenames = fileMetaProvider.checkFilesExistsByHash(hash);

        if (storedFilenames.isEmpty() || !storedFilenames.contains(filename)) {
            throw new IOException("file not found");
        }

        if (storedFilenames.size() == 1) {
            String ext = FilenameUtils.getExtension(filename);
            String fullFileName = hash.toString() + "." + ext;
            systemProvider.deleteFile(fullFileName);
        }

        fileMetaProvider.deleteMetaFile(hash, filename);
        return new FileMetaDTO(hash, filename);
    }
}
