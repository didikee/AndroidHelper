package didikee.github.share;

import java.util.ArrayList;

public final class ShareType {
    /**
     * Share Text
     */
    public static final String TEXT = "text/plain";

    /**
     * Share Image
     */
    public static final String IMAGE = "image/*";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_GIF = "image/gif";

    /**
     * Share Audio
     */
    public static final String AUDIO = "audio/*";
    public static final String AUDIO_MP3 = "audio/mp3";

    /**
     * Share Video
     */
    public static final String VIDEO = "video/*";
    public static final String VIDEO_MP4 = "video/mp4";

    /**
     * Share File
     */
    public static final String FILE = "*/*";

    public static ArrayList<String> getImageTypes() {
        ArrayList<String> images = new ArrayList<>();
        images.add(IMAGE);
        images.add(IMAGE_GIF);
        images.add(IMAGE_PNG);
        images.add(IMAGE_JPG);
        return images;
    }

    public static ArrayList<String> getVideoTypes() {
        ArrayList<String> videos = new ArrayList<>();
        videos.add(VIDEO);
        videos.add(VIDEO_MP4);
        return videos;
    }

    public static ArrayList<String> getAudioTypes() {
        ArrayList<String> audios = new ArrayList<>();
        audios.add(AUDIO);
        audios.add(AUDIO_MP3);
        return audios;
    }

    public static String getRootType(String type) {
        if (TEXT.equalsIgnoreCase(type)) {
            return TEXT;
        }
        ArrayList<String> imageTypes = getImageTypes();
        if (imageTypes.contains(type)) {
            return IMAGE;
        }
        ArrayList<String> videoTypes = getVideoTypes();
        if (videoTypes.contains(type)) {
            return VIDEO;
        }
        ArrayList<String> audioTypes = getAudioTypes();
        if (audioTypes.contains(type)) {
            return AUDIO;
        }
        return FILE;
    }
}
