package br.com.ceppantoja.cursomc.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class S3Service {

    Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;
    @Value("${s3.bucket}")
    private String bucketName;

    public void uploadFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            this.LOG.info("Iniciando upload");
            this.s3client.putObject(new PutObjectRequest(this.bucketName, "ralph", file));
            this.LOG.info("Upload finalizado");
        } catch(AmazonServiceException e) {
            this.LOG.info("AmazonServiceException: "+e.getErrorMessage());
            this.LOG.info("Status Code: " + e.getStatusCode());
        } catch(AmazonClientException e) {
            this.LOG.info("AmazonClientException: " + e.getMessage());
        }
    }
}
