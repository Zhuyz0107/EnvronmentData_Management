package com.briup.others;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * @author Hlmove
 * @date 创建日期 2023/9/26 16:38
 * @Description back
 * @Note
 */
public class BackImpl  {
    //第二次读直接从长度开始读
     private  static final String PATH = "env/backup";
    /**
     *
     * @param filePath 备份文件的路径
     * @param del      读取完备份文件后是否要删除此备份文件，true为删除，false为不删除。
     *                 在调用时推荐使用本接口中定义的静态常量LOAD_REMOVE、LOAD_UNREMOVE
     * @return 返回一个long类型
     * @throws Exception 抛出异常
     */

    public Object load(String filePath, boolean del) throws Exception {
        //定义文件
        File file =new File(PATH,filePath);
        //如果文件不存在，返回0
        if (!file.exists()){
            return 0L;
        }
        //br读取文件
        BufferedReader br  =new BufferedReader(new FileReader(file));
        //读取的数据
        String line = br.readLine();
        //如果要删除就删除文件
        if (del){
            boolean delete = file.delete();
            System.out.println("文件是否删除:" + delete);
        }
        return line;
    }

    /**
     *
     * @param filePath 备份文件的路径
     * @param obj      将要写入备份文件的对象
     * @param append   在写入时追加还是覆盖，true为追加，false为覆盖,在调用时推荐使用接口中定义的静态常量STORE_APPEND、STORE_OVERRIDE
     * @throws Exception  抛出异常
     */

    public void store(String filePath, Object obj, boolean append) throws Exception {
        //定义一个接收的对象的长度
        Long objLong;
        //如果对象不为空就 将其长度给出并返回
        if (obj!=null){
            objLong = Long.valueOf(obj.toString());
        }else {
            objLong= 0L;
        }
        //定义文件
        File file  =new File(PATH,filePath);
        //如果文件不存在就创建文件
        if (!file.exists()){
            boolean newFile = file.createNewFile();
            System.out.println("文件不存在，创建文件" + newFile);
        }
        //调用load方法获取现在的文件存的值
        BackImpl back  = new BackImpl();
        Object obj1 = back.load(filePath, false);
        long load;
        //如果文件不为空就将其值赋值到load
        if (obj1!=null){
            load = Long.parseLong(obj1.toString());
        }else {
            load =0;
        }
        //将数据写入文件之中
        PrintWriter  pw=  new PrintWriter(file);
        //判断是否追加
        if (append){
            pw.println(objLong+load);
            pw.flush();
        }else {
            pw.println(objLong);
        }

    }
}
