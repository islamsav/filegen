# With this library you can create pictures

### 1. The first type creates a random picture
``` java
        new Image().random("target/file.jpeg");
```
OR
``` java
        new Image().random("file.jpeg");
```

### 2. Second type via constructor
``` java
        new Image(new Dimension(500, 700), Color.GREEN, "123.jpeg", "444444444", Color.BLACK, 50);
```
OR
``` java
        new Image(new Dimension(500, 700), Color.RED, "file2.png");
```

### 3. Third type through builder
``` java
        new Image()
                .color(Color.WHITE)
                .format("png")
                .size(600, 800)
                .text("ЭТО ТЕКСТЭТО ТЕКСТ", Color.RED, 32)
                .text("text", Color.GREEN, 50)
                .path("image2.png")
                .build();
```

##### _Methods random() and build() return Path instance_
