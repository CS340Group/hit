package model.productidentifier;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

public class PluginLoader {

    private static final String PLUGIN_CONFIG = "./plugins.json";

    public static void load(){
        Data data = null;
        try {
            data = new Gson().fromJson(readFile(PLUGIN_CONFIG), Data.class);
        } catch (IOException e){
            System.err.println("An error was caught when trying to read plugins.json. Are you sure it exists?");
            e.printStackTrace(System.err);
        }
        for(PluginData plugin : data.getPlugins()){
            ProductIdentificationPluginRegistry.RegisterPlugin(
                    new PluginDescriptor(
                            plugin.getName(),
                            plugin.getClassName(),
                            plugin.getDescription()
                    )
            );
        }
    }

    private static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            /* Instead of using default, pass in a decoder. */
            return Charset.defaultCharset().decode(bb).toString();
        }
        finally {
            stream.close();
        }
    }

    private class PluginData {
        private String name;
        private String className;
        private String description;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    private class Data {
        private List<PluginData> plugins;

        public List<PluginData> getPlugins() {
            return plugins;
        }

        public void setPlugins(List<PluginData> plugins) {
            this.plugins = plugins;
        }
    }
}
