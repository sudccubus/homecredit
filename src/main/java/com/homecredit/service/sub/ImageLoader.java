package com.homecredit.service.sub;

import com.homecredit.web.exception.IncorrectDataException;
import liquibase.util.StringUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;

@Component
public class ImageLoader {

    private final static String INCORRECT_IMAGE_MESSAGE = "Incorrect image uploaded";
    private final static String POINT_DELIMITER = ".";

    @Value("${storage.imageLocation}")
    private String imageLocation;

    @SneakyThrows
    public String uploadImage(MultipartFile image, String existImageName) {
        if (isNull(image) || image.isEmpty()) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String originalFilename = image.getOriginalFilename();
        if (StringUtil.isEmpty(originalFilename)) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String contentType = image.getContentType();
        if (StringUtil.isEmpty(contentType)) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String newImageName = generateFilename(contentType);
        File newFile = getFileWithDirectoryAssurance(getStorageDirectory(), newImageName);
        image.transferTo(newFile);

        if (isExistFile(existImageName)) {
            File oldFile = getFileWithDirectoryAssurance(getStorageDirectory(), existImageName);
            oldFile.delete();
        }

        return newImageName;
    }

    private String generateFilename(String contentType) {
        String fileExtension = MimeTypeUtils.parseMimeType(contentType).getSubtype();
        String newImageNameUUID = UUID.randomUUID().toString();

        return String.join(POINT_DELIMITER, newImageNameUUID, fileExtension);
    }

    private String getStorageDirectory() {
        return imageLocation.substring(imageLocation.length() - 1).equals(File.separator)
                ? imageLocation
                : imageLocation + File.separator;
    }

    private File getFileWithDirectoryAssurance(String directory, String filename) {
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();

        return new File(directory + filename);
    }

    private boolean isExistFile(String filename) {
        if (StringUtil.isEmpty(filename)) return false;

        String[] filenameList = Objects.requireNonNull(new File(getStorageDirectory()).list());

        return Arrays.asList(filenameList).contains(filename);
    }
}