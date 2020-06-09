package imageBucket;



public enum Bucket {

    USER_IMAGE("name of bucket on aws");

    private final String name;

    private Bucket(String name) {
        this.name = name;
    }

    public String getBucketName() {
        return name;
    }
}