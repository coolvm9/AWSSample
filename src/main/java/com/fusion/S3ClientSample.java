package com.fusion;


import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class S3ClientSample {
    private static String dev_user_profile = "AWSSAMCLIUserDev1";

    public static void main(String[] args) {
        try {
           /*
           AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
  "your_access_key_id",
  "your_secret_access_key");
S3Client s3 = S3Client.builder()
            .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
            .build();
            */

            Region region = Region.US_WEST_1;
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .credentialsProvider(ProfileCredentialsProvider.create(dev_user_profile))
                    .build();
            String bucket_name = "satyatest1212121";
            createBucket(bucket_name, s3);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void createBucket(String bucketName, S3Client s3) {
        S3Waiter s3Waiter = s3.waiter();
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket(bucketName)
                .build();
        s3.createBucket(bucketRequest);
        HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                .bucket(bucketName)
                .build();
        // Wait until the bucket is created and print out the response.
        WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
        waiterResponse.matched().response().ifPresent(System.out::println);
        System.out.println(bucketName + " is ready");
    }


}
