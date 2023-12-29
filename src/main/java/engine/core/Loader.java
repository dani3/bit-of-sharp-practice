package engine.core;

import engine.renderer.buffer.VertexArray;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private static final Logger mLogger = Logger.create(Loader.class.getName());

    /**
     * Load a model given an .obj file.
     *
     * @param filename name of the .obj file (including the extension).
     * @return VertexArray object.
     * @throws FileNotFoundException Thrown if the file doesn't exist.
     */
    public static VertexArray fromObjFile(final String filename) throws FileNotFoundException {
        var path = FileUtils.getResourcePath(FileUtils.RESOURCE_TYPE_OBJECTS, filename);
        BufferedReader br = new BufferedReader(new FileReader(path));

        List<Vector3f> vertexPositions = new ArrayList<>();
        List<Vector3f> textureCoordinates = new ArrayList<>();
        List<Vector3f> vertexNormals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        try {
            String line;
            while (((line = br.readLine()) != null)) {
                // Vertex positions
                if (line.startsWith("v ")) {
                    var vertices = line.split(" ");
                    vertexPositions.add(
                            new Vector3f(
                                    Float.parseFloat(vertices[1]),
                                    Float.parseFloat(vertices[2]),
                                    Float.parseFloat(vertices[3])
                            )
                    );
                }

                // Texture coordinates
                if (line.startsWith("vt ")) {
                    var textureCoordinates = line.split(" ");
                    vertexPositions.add(
                            new Vector3f(
                                    Float.parseFloat(texCoord[1]),
                                    Float.parseFloat(texCoord[2]),
                                    Float.parseFloat(texCoord[3])
                            )
                    );
                }

                // Vertex normals
                if (line.startsWith("vn ")) {
                    var normals = line.split(" ");
                    vertexNormals.add(Float.parseFloat(normals[1]));
                    vertexNormals.add(Float.parseFloat(normals[2]));
                    vertexNormals.add(Float.parseFloat(normals[3]));
                }

                // Faces
                if (line.startsWith("f ")) {
                    var faces = line.split(" ");
                    indices.add(Integer.parseInt(faces[1]));
                    indices.add(Integer.parseInt(faces[2]));
                    indices.add(Integer.parseInt(faces[3]));
                }
            }

        } catch (IOException e) {
            mLogger.error("Error reading .obj file");
        }

        return null;
    }
}
