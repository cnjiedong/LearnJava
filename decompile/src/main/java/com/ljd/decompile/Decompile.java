package com.ljd.decompile;

/**
 * @ClassName: Decompile
 * @Description: (反编译jar包) 借助第三方反编译程序cfr_0_125.jar，对制定目录下的所有jar包进行反编译
                    http://www.benf.org/other/cfr/index.html
 * @Author jd
 * @Date 2018-04-13
 * @Version V1.0
 * @Copyright jd Co.,Ltd
 */
import java.io.File;
import java.io.IOException;
import org.benf.cfr.reader.Main;
public class Decompile {
    int jarCount = 0;
    String [] arguments;
    public static void main(String[] argv){

        try {
            if(!checkArgs(argv)){
                return;
            }
            Decompile decompile = new Decompile(argv);
            decompile.executeDir(argv[0], argv[1]);
            System.out.println(decompile.getJarCount() + "jars have been compiled");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean checkArgs(String[] argv){
        if(argv.length<2){
            System.out.println("输入参数不足");
            return false;
        }
        System.out.println(argv);
        String sourcePath = argv[0];
        String outputPath = argv[1];
        File file = new File(sourcePath);
        if(!file.exists()){
            System.out.println("[" + sourcePath + "]不存在");
            return false;
        }
        if(file.isFile() && !sourcePath.endsWith(".jar") && !sourcePath.endsWith(".class")){
            System.out.println("文件[" + sourcePath + "]不是jar或class文件");
            return false;
        }
        /*File output = new File(outputPath);
        if(!output.isDirectory()){
            System.out.println("目录[" + outputPath + "]不存在");
            return false;
        }*/
        System.out.println("sourcePath:" + sourcePath);
        System.out.println("outputPath:" + outputPath);
        return true;
    }
    public Decompile(String[] arguments) {
        this.arguments = arguments;
    }

    public void executeDir(String sourcePath, String outputPath) throws IOException{
        File dirFile = new File(sourcePath);
        if (!dirFile.exists()) {
            System.out.println("[" + sourcePath + "] do not exit");
            return ;
        }

        if(dirFile.isFile()){
            executeFile( sourcePath, outputPath);
            return ;
        }
        String[] fileList = dirFile.list();
        for(String filename : fileList){
            String fullFilename = sourcePath + "/" + filename;
            File file = new File(fullFilename);
            if(file.isDirectory()){
                String output = outputPath + "/" + filename;
                executeDir(fullFilename, output);
                continue;
            }
            if(!file.isFile()){
                continue;
            }

            if(fullFilename.endsWith(".jar") || fullFilename.endsWith(".class")) {
                executeFile( fullFilename, outputPath);
            }
        }
        return ;
    }

    public void executeFile(String sourcePath, String outputPath) throws IOException{
        File dir = new File(outputPath);
        if(!dir.exists()){
            if(!dir.mkdirs()){
                System.out.println("创建目录失败[" + outputPath + "]");
            }
        }
        File file = new File(sourcePath);
        if(file.isFile()){
            if(!sourcePath.endsWith(".jar") && !sourcePath.endsWith(".class")){
                return ;
            }
            System.out.println(file.getCanonicalFile());
            runCompile(sourcePath, outputPath);
            jarCount++;
        }
    }

    void runCompile(String jar, String outputPath) {
        //String[] argv = new String[]{"d:\\workspace\\decompile",jar, "--outputdir" , outputPath};
        String[] argv = new String[]{jar, "--outputdir" , outputPath};

        Main.main(argv);
    }

    public int getJarCount() {
        return jarCount;
    }

    public void setJarCount(int jarCount) {
        this.jarCount = jarCount;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }
}
