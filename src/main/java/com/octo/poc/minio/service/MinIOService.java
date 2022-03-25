package com.octo.poc.minio.service;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinIOService {

    String endPoint = "http://127.0.0.1:9000";
    String accessKey = "admin";
    String secretKey = "password";
    String bucketName = "bucket-test";
    String localFileFolder = "";

    public void uploadToMinio(String fileName) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endPoint)
                    .credentials(accessKey, secretKey)
                    .build();

            boolean isBucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isBucketExists) {
                minioClient.makeBucket(MakeBucketArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
            }

            String fileToUpload = localFileFolder + fileName;
            UploadObjectArgs args = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .filename(fileToUpload)
                    .build();
            minioClient.uploadObject(args);

            System.out.println(fileToUpload + " successfully uploaded !");


        } catch (MinioException e) {
            System.err.println("Error : " + e);
        }

    }

    public void downloadFromMinio(String fileName) throws MinioException{
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(endPoint)
                    .credentials(accessKey, secretKey)
                    .build();

            String downloadedFile = localFileFolder + "downloaded_" + fileName;
            DownloadObjectArgs args = DownloadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .filename(downloadedFile)
                    .build();
            minioClient.downloadObject(args);

            System.out.println("Downloaded file to " + downloadedFile + " Successfully");
        } catch (MinioException e) {
            System.err.println("Error occurred : " + e);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

}
