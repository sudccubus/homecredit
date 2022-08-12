package com.homecredit.utils;

import com.homecredit.web.exception.IncorrectDataException;
import liquibase.util.StringUtil;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

import static java.util.Objects.isNull;

@UtilityClass
public class ImageUtil {

    private final static String INCORRECT_IMAGE_MESSAGE = "Incorrect image uploaded";
    private final static String POINT_DELIMITER = ".";

    @Value("${storage.imageLocation}")
    private String imageLocation;

    @SneakyThrows
    public static String uploadImage(MultipartFile image) {
        if (isNull(image) || image.isEmpty()) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String originalFilename = image.getOriginalFilename();
        if (StringUtil.isEmpty(originalFilename)) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String contentType = image.getContentType();
        if (StringUtil.isEmpty(contentType)) throw new IncorrectDataException(INCORRECT_IMAGE_MESSAGE);

        String fileExtension = MimeTypeUtils.parseMimeType(contentType).getSubtype();
        String newImageNameUUID = UUID.randomUUID().toString();
        String newImageName = String.join(POINT_DELIMITER, newImageNameUUID, fileExtension);

        File file = fileWithDirectoryAssurance(imageLocation, newImageName);

        image.transferTo(file);
        return newImageName;
    }

    private File fileWithDirectoryAssurance(String directory, String filename) {
        File dir = new File(directory);
        if (!dir.exists()) dir.mkdirs();
        return new File(directory + filename);
    }
}
