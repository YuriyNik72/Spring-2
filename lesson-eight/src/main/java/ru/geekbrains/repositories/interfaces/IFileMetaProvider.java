package ru.geekbrains.repositories.interfaces;

import ru.geekbrains.entites.FileMetaDTO;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IFileMetaProvider {

    String checkFileExists(UUID fileHash, String fileName);


    /**
     * Сохранить метаданные файла
     *
     */
    void saveFileMeta(UUID Hash, String fileName, int sybType);

    Collection<FileMetaDTO> getMetaFiles(int subtype);

    void deleteMetaFile(UUID fileHash, String fileName);

    List<String> checkFilesExistsByHash(UUID fileHash);
}
