package com.music.service.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.music.util.AttachedFile;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

import static org.apache.commons.io.FilenameUtils.getName;

@Service
@NoArgsConstructor
public class S3Service {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct // 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이며, bean 이 한 번만 초기화 될수 있도록 해줌
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    /* Get */
    public S3Object get(String key) {
        GetObjectRequest request = new GetObjectRequest(bucket, key);
        return s3Client.getObject(request);
    }

    /* Upload */
    public String upload(AttachedFile file, String key, Map<String, String> metadata) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.length());
        objectMetadata.setContentType(file.getContentType());

        if (metadata != null && !metadata.isEmpty()) {
            objectMetadata.setUserMetadata(metadata);
        }

        PutObjectRequest request = new PutObjectRequest(bucket, key, file.inputStream(), objectMetadata);

        s3Client.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, key).toString();
    }

    /* Delete */
    public void delete(String url) {
        String key = getName(url);
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, key);
        s3Client.deleteObject(request);
    }

}
