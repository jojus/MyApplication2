package loyola.alex.com.studentcircularalert;

/**
 * Created by Justin Joy (jojus) on 23-09-2017.
 */

class DataModel {

    private String name;
    private String version;
    private int id;
    private int image;

    DataModel(String name, String version, int id, int image) {
        this.name = name;
        this.version = version;
        this.id = id;
        this.image=image;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}

