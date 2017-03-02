// CommonInterface.aidl
package CommonPackage;

// Declare any non-default types here with import statements

interface CommonInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void playservice(int tracknum);
    List<String> getlogs();
    void pauseservice(int tracknum);
    void stopservice(int tracknum);
    void resumeservice(int tracknum);
}
