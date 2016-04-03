package dtv.peer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

/**
 *
 * 
 */
public class extendFileChooser{
    
} 

class Utils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";   
    public final static String png = "png";
    public final static String doc = "doc";
    public final static String docx = "docx";
    public final static String ppt = "ppt";
    public final static String pptx = "pptx";
    public final static String xls = "xls";
    public final static String xlsx = "xlsx";
    public final static String pdf = "pdf";
    public final static String html = "html";
    public final static String psd = "psd";
    public final static String binary = "binary";
    public final static String mp3 = "mp3";
    public final static String mp4 = "mp4";
    public final static String avi = "avi";
    public final static String flv = "flv";
    public final static String wav = "wav";
    public final static String winrar = "winrar";
    public final static String zip = "zip";
    public final static String txt = "txt";
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }
}

///////////////////////GIF//////////////////////////////////////
class Filegif extends FileFilter  {
   private String gifFile = "GIF";
      
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(gifFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "GIF(*.gif)";
    }
    
}

////////////////////////////JPEG////////////////////////
class Filejpeg extends FileFilter  {

    private String jpegFile = "JPEG";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(jpegFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "JPEG(*.jpeg)";
    }
    
}

//////////////////////////////////////////JPG////////////////////////
class Filejpg extends FileFilter  {

    private String jpgFile = "JPG";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(jpgFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "JPG(*.jpg)";
    }
    
}

///////////////////////////PNG//////////////////////////////////
class Filepng extends FileFilter  {

    private String pngFile = "PNG";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(pngFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "PNG(*.png)";
    }
    
}

////////////////////////////////DOC DOCX/////////////////////////////
class Filedoc extends FileFilter  {

    private String docFile = "DOC";
    private String docxFile = "DOCX";
   
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(docFile)||extension(file).equalsIgnoreCase(docxFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "Word(*.doc, *.docx)";
    }
    
}
////////////////////////////////PPT PPTX/////////////////////////////
class Fileppt extends FileFilter  {

    private String pptFile = "PPT";
    private String pptxFile = "PPTX";
   
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(pptFile)||extension(file).equalsIgnoreCase(pptxFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "Power Point(*.ppt, *.pptx)";
    }
    
}
////////////////////////////////DOC DOCX/////////////////////////////
class Filexls extends FileFilter  {

    private String xlsFile = "XLS";
    private String xlsxFile = "XLSX";
   
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(xlsFile)||extension(file).equalsIgnoreCase(xlsxFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "Excel(*.xls, *.xlsx)";
    }
    
}
/////////////////////////////MP3/////////////////////////////
class Filemp3 extends FileFilter  {

    private String mp3File = "MP3";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(mp3File))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "MP3(*.mp3)";
    }
    
}

//////////////////////////////////WMV//////////////////////////////
class Filewmv extends FileFilter  {

    private String wmvFile = "WMV";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(wmvFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "WMV(*.wmv)";
    }
    
}

////////////////////////////////AVI////////////////////////////
class Fileavi extends FileFilter  {

    private String aviFile = "AVI";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(aviFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "AVI(*.avi)";
    }
    
}

///////////////////////////FLV///////////////////////////////////
class Fileflv extends FileFilter  {

    private String flvFile = "FLV";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(flvFile))
            
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "FLV(*.flv)";
    }
    
}

///////////////////////////////////////MP4/////////////////////////
class Filemp4 extends FileFilter  {

    private String mp4File = "MP4";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(mp4File))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "MP4(*.mp4)";
    }
    
}

//////////////////////////////////HTML//////////////////////////////////////

class Filehtml extends FileFilter  {

    private String htmlFile = "HTML";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(htmlFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "HTML(*.html)";
    }
    
}

class Filewinrar extends FileFilter  {

    private String winrarFile = "WINRAR";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(winrarFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "WINRAR(*.rar)";
    }
    
}
class Filepdf extends FileFilter  {

    private String pdfFile = "PDF";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(pdfFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "PDF(*.pdf)";
    }
    
}
//////////////////ZIP/////////////////////////////////
class Filezip extends FileFilter  {

    private String zipFile = "ZIP";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(zipFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "ZIP(*.zip)";
    }
    
}
/////////////////////////////////////////////////////////////////
class Filepsd extends FileFilter  {

    private String psdFile = "PSD";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(psdFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "PSD(*.PSD)";
    }
    
}

////////////////////////////////////////////////////////////////
class Filetxt extends FileFilter  {

    private String txtFile = "TXT";
    
    
    public String extension(File file){
        String fileName=file.getName();
        int indexFile=fileName.lastIndexOf(".");
        if ((indexFile>0)&&(indexFile<fileName.length()-1)){
            return fileName.substring(indexFile+1);
        }
        return "";
    }
    @Override
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if (extension(file).equalsIgnoreCase(txtFile))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "TXT(*.TXT)";
    }
    
}
/////////////////////Image Type////////////////////////////////

class ImageFileView extends FileView {
    ImageIcon jpegIcon = Utils.createImageIcon("../picture/jpegType.png");
    ImageIcon jpgIcon = Utils.createImageIcon("../picture/jpgType.png");
    ImageIcon gifIcon = Utils.createImageIcon("../picture/gifType.png");   
    ImageIcon pngIcon = Utils.createImageIcon("../picture/pngType.png");
    ImageIcon docIcon = Utils.createImageIcon("../picture/docType.png");
    ImageIcon pptIcon = Utils.createImageIcon("../picture/pptType.png");
    ImageIcon xlsIcon = Utils.createImageIcon("../picture/xlsType.png");
    ImageIcon musicIcon = Utils.createImageIcon("../picture/musicType.png");
    ImageIcon mvIcon = Utils.createImageIcon("../picture/aviType.png");
    ImageIcon pdfIcon = Utils.createImageIcon("../picture/pdfType.png");
    ImageIcon psdIcon = Utils.createImageIcon("../picture/psdType.png");
    ImageIcon htmlIcon = Utils.createImageIcon("../picture/htmlType.png");
    ImageIcon binaryIcon = Utils.createImageIcon("../picture/binaryType.png");
    ImageIcon txtIcon = Utils.createImageIcon("../picture/txtType.png");
    ImageIcon winrarIcon = Utils.createImageIcon("../picture/winrarType.png");

    public String getName(File f) {
        return null; //let the L&F FileView figure this out
    }

    public String getDescription(File f) {
        return null; //let the L&F FileView figure this out
    }

    public Boolean isTraversable(File f) {
        return null; //let the L&F FileView figure this out
    }

    public String getTypeDescription(File f) {
        String extension = Utils.getExtension(f);
        String type = null;

        if (extension != null) {
            switch (extension) {
                case Utils.jpeg:
                    type = "JPEG Image";
                    break;
                case Utils.jpg:
                    type = "JPG Image";
                    break;
                case Utils.gif:
                    type = "GIF Image";
                    break;
                case Utils.png:
                    type = "PNG Image";
                    break;
                case Utils.doc:
                case Utils.docx:
                     type="DOC Image";
                     break;
                case Utils.ppt:
                case Utils.pptx:
                     type="PPT Image";
                     break;
                case Utils.xls:
                case Utils.xlsx:
                     type="XLS Image";
                     break;
                case Utils.flv:
                case Utils.mp4:
                case Utils.avi:
                    type="AVI Imager";
                    break;
                case Utils.wav:
                case Utils.mp3:
                    type="MUSIC Image";
                    break;
                case Utils.pdf:
                    type="PDF Image";
                    break;
                case Utils.winrar:
                case Utils.zip:
                    type="ZIP Image";
                    break;
                case Utils.psd:
                    type="PSD Image";
                    break;
                case Utils.html:
                    type="HTML Image";
                    break;
                case Utils.txt:
                    type="TXT Image";
                    break;
                default:
                    type="BINARY Image";
                    break;
            }
        }
        return type;
    }

    public Icon getIcon(File f) {
        String extension = Utils.getExtension(f);
        Icon icon = null;

        if (extension != null) {
            switch (extension) {
                case Utils.jpeg:
                    icon = jpegIcon;
                    break;
                case Utils.jpg:
                    icon = jpgIcon;
                    break;
                case Utils.gif:
                     icon = gifIcon;
                    break;
                case Utils.png:
                     icon = pngIcon;
                    break;
                case Utils.doc:
                case Utils.docx:
                      icon = docIcon;
                     break;
                case Utils.ppt:
                case Utils.pptx:
                      icon = pptIcon;
                     break;
                case Utils.xls:
                case Utils.xlsx:
                      icon = xlsIcon;
                     break;
                case Utils.flv:
                case Utils.mp4:
                case Utils.avi:
                     icon = mvIcon;
                    break;
                case Utils.wav:
                case Utils.mp3:
                    icon = musicIcon;
                    break;
                case Utils.pdf:
                     icon = pdfIcon;
                    break;
                case Utils.winrar:
                case Utils.zip:
                    icon = winrarIcon;
                    break;
                case Utils.psd:
                    icon = psdIcon;
                    break;
                case Utils.html:
                    icon = htmlIcon;
                    break;
                case Utils.txt:
                    icon = txtIcon;
                    break;
                default:
                    icon = binaryIcon;
                    break;
                
            }
        }
        return icon;
    }
}