package didikee.github.helper.helper;

import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import didikee.github.helper.file.SerializationUtil;

/**
 * Created by didikee on 23/05/2017.
 */

public abstract class AbsSerializationHelper<T extends Serializable> {

    private static final String TAG = AbsSerializationHelper.class.getSimpleName();

    /**
     *
     * @param context 用于获取缓存路径,而非sd卡路径
     * @return 缓存路径
     */
    protected abstract String getSavePath(Context context);

    public boolean save(Context context, T obj) {
        if (!context.getFilesDir().exists()) {
            context.getFilesDir().mkdir();
        }
        FileOutputStream ops = null;
        try {
            ops = new FileOutputStream(getSavePath(context));
            byte[] output_data = SerializationUtil.serialize(obj);
            ops.write(output_data, 0, output_data.length);
            ops.flush();
            return true;
        } catch (IOException e) {
            Log.e(TAG, e.getLocalizedMessage());
            return false;
        } finally {
            try {
                ops.close();
            } catch (IOException e) {
            }
        }
    }

    public T get(Context context) {
        if (!context.getFilesDir().exists()) {
            return null;
        }
        File file = new File(getSavePath(context));
        if (!file.exists()) {
            return null;

        }
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return (T) SerializationUtil.deserialize(bytes);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void clear(Context context) {
        if (context.getFilesDir().exists()) {
            File file = new File(getSavePath(context));
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
