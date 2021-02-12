package me.williamisnthere.bot;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;

public class Config {


    private JsonObject object;

    public Config() {
        try {
            Reader reader = new FileReader(Paths.get("config.json").toFile());
            this.object = (JsonObject) Jsoner.deserialize(reader);
        } catch (FileNotFoundException | JsonException e) {
            e.printStackTrace();
        }

    }


    public String get(String what) {
        return String.valueOf(object.get(what));
    }

    public boolean getBoolean(String what) {
        return Boolean.parseBoolean(what);
    }



}
