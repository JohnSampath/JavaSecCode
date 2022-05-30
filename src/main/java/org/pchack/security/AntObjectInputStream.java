package org.pchack.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * RASP：Hook resolveClass method of java/io/ObjectInputStream class
 * RASP: https://github.com/baidu/openrasp/blob/master/agent/java/engine/src/main/java/com/baidu/openrasp/hook/DeserializationHook.java
 *
 * Run main method to test.
 */
public class AntObjectInputStream extends ObjectInputStream {

    protected final Logger logger= LoggerFactory.getLogger(AntObjectInputStream.class);

    public AntObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
    }

    /**
     * Only allow deserialization of SerialObject class
     *
     * It is relatively limited to use the black and white list verification scheme in applications, because the verification can only be 
     * performed by deserialization using the AntObjectInputStream class defined by yourself.
     * Deserialization of generic classes like fastjson cannot be verified.
     * But RASP is a global detection whitelist through the resolveClass method of the HOOK java/io/ObjectInputStream class.
     *
     */
    @Override
    protected Class<?> resolveClass(final ObjectStreamClass desc)
            throws IOException, ClassNotFoundException
    {
        String className = desc.getName();

        // Deserialize class name: org.pchack.security.AntObjectInputStream$MyObject
        logger.info("Deserialize class name: " + className);

        String[] denyClasses = {"java.net.InetAddress",
                                "org.apache.commons.collections.Transformer",
                                "org.apache.commons.collections.functors"};

        for (String denyClass : denyClasses) {
            if (className.startsWith(denyClass)) {
                throw new InvalidClassException("Unauthorized deserialization attempt", className);
            }
        }

        return super.resolveClass(desc);
    }

    public static void main(String args[]) throws Exception{
        // Define the myObj object
        MyObject myObj = new MyObject();
        myObj.name = "world";

        // Create a /tmp/object data file containing the object deserialization information
        FileOutputStream fos = new FileOutputStream("/tmp/object");
        ObjectOutputStream os = new ObjectOutputStream(fos);

        // Create a /tmp/object data file containing the object deserialization information
        os.writeObject(myObj);
        os.close();

        // Deserialize obj object from file
        FileInputStream fis = new FileInputStream("/tmp/object");
        AntObjectInputStream ois = new AntObjectInputStream(fis);  // AntObjectInputStream class

        //Restoring the object is deserializing
        MyObject objectFromDisk = (MyObject)ois.readObject();
        System.out.println(objectFromDisk.name);
        ois.close();
    }

    static class  MyObject implements Serializable {
        public String name;
    }
}


