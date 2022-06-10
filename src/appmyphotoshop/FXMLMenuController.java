package appmyphotoshop;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;


public class FXMLMenuController implements Initializable 
{
    @FXML
    private ImageView imageView;
    private Image image=null;
    private BufferedImage bimage;
    private ImagePlus imagePlus;
    private File file=null;
    private Boolean flag;
    
    @FXML
    private MenuItem imAbrir;
    @FXML
    private MenuItem imSalvar;
    @FXML
    private MenuItem imSalvarComo;
    @FXML
    private MenuItem imFechar;
    @FXML
    private Menu meFiltros;
    @FXML
    private Menu meImageJ;
    @FXML
    private Button btAbrir;
    @FXML
    private Button btSalvar;
    @FXML
    private Button btFechar;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void evtAbrir(ActionEvent event) 
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Todos os arquivos", "*.*"),
            new FileChooser.ExtensionFilter("JPEG (*.jpeg)", "*.jpeg"),
            new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"),
            new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif")
        );
        file = fileChooser.showOpenDialog(null);
        if (file!=null)
        {   
            image = new Image(file.toURI().toString());
            imageView.setFitHeight(image.getHeight());
            imageView.setFitWidth(image.getWidth());
            imageView.setImage(image);
            flag = false;
            imAbrir.setDisable(true);
            btAbrir.setDisable(true);
            imSalvarComo.setDisable(false);
            imFechar.setDisable(false);
            btFechar.setDisable(false);
            meFiltros.setDisable(false);
            meImageJ.setDisable(false);
        }   
    }

    @FXML
    private void evtSalvar(ActionEvent event) 
    {
        String extensao;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(file.getName());
        try
        {
            extensao = file.getName().substring(1 + file.getName().lastIndexOf(".")).toLowerCase();
            ImageIO.write(bimage, extensao, file);
        }
        catch (Exception e)
        {
            
        }
        imSalvar.setDisable(true);
        btSalvar.setDisable(true);
        flag = false;
    }
    
    @FXML
    private void evtSalvarComo(ActionEvent event) 
    {
        String extensao;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(file.getName());
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG (*.jpeg)", "*.jpeg"),
            new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG (*.png)", "*.png"),
            new FileChooser.ExtensionFilter("GIF (*.gif)", "*.gif")
        );
        File file = fileChooser.showSaveDialog(null);
        if (file!=null)
        {
            try
            {
                extensao = file.getName().substring(1 + file.getName().lastIndexOf(".")).toLowerCase();
                ImageIO.write(bimage, extensao, file);
            }
            catch (Exception e)
            {

            }
            imSalvar.setDisable(true);
            btSalvar.setDisable(true);
            this.file = file;
            flag = false;
        }
    }

    @FXML
    private void evtFechar(ActionEvent event) 
    {
        if (flag==true)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fechar Imagem");
            alert.setHeaderText("Imagem com alteração!");
            alert.setContentText("Foram realizadas alterações na imagem, deseja salvar?");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.get() == ButtonType.OK)
            {
                evtSalvarComo(event);
                imageView.setImage(null);
            }
            else if (resultado.get() == ButtonType.CANCEL)
            {
                imageView.setImage(null);
                imAbrir.setDisable(false);
                btAbrir.setDisable(false);
                imSalvar.setDisable(true);
                btSalvar.setDisable(true);
                imSalvarComo.setDisable(true);
                imFechar.setDisable(true);
                btFechar.setDisable(true);
                meFiltros.setDisable(true);
                meImageJ.setDisable(true);
                flag = false;
            }
        }
        else
        {
            imageView.setImage(null);
            imAbrir.setDisable(false);
            btAbrir.setDisable(false);
            imSalvar.setDisable(true);
            btSalvar.setDisable(true);
            imSalvarComo.setDisable(true);
            imFechar.setDisable(true);
            btFechar.setDisable(true);
            meFiltros.setDisable(true);
            meImageJ.setDisable(true);
            flag = false;
        }
    }

    @FXML
    private void evtSair(ActionEvent event) 
    {
        Platform.exit();
    }

    @FXML
    private void evtTonsCinza(ActionEvent event) 
    {
        int media;
        bimage = SwingFXUtils.fromFXImage(image, null);
        int pixel[] = {0,0,0,0};
        WritableRaster raster=bimage.getRaster();
        for (int y=0; y<image.getHeight(); y++)
            for (int x=0; x<image.getWidth(); x++)
            {
                raster.getPixel(x, y, pixel);
                media=(int)(pixel[0]*0.3 + pixel[1]*0.59 + pixel[2]*0.11);
                pixel[0]=pixel[1]=pixel[2]=media;
                raster.setPixel(x, y, pixel);
            }
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtTonsPretoeBranco(ActionEvent event) 
    {
        int media;
        bimage = SwingFXUtils.fromFXImage(image, null);
        int pixel[] = {0,0,0,0};
        WritableRaster raster = bimage.getRaster();
        for (int y=0; y<image.getHeight(); y++)
            for (int x=0; x<image.getWidth(); x++)
            {
                raster.getPixel(x, y, pixel);
                media = (pixel[0]+pixel[1]+pixel[2])/3;
                if (media>127)
                {
                    pixel[0] = 255;
                    pixel[1] = 255;
                    pixel[2] = 255;
                    raster.setPixel(x, y, pixel);
                }
                else
                {
                    pixel[0] = 0;
                    pixel[1] = 0;
                    pixel[2] = 0;
                    raster.setPixel(x, y, pixel);
                }
            }
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtTonsNegativos(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        int pixel[] = {0,0,0,0};
        WritableRaster raster = bimage.getRaster();
        for (int y=0; y<image.getHeight(); y++)
            for (int x=0; x<image.getWidth(); x++)
            {
                raster.getPixel(x, y, pixel);
                pixel[0] = 255 - pixel[0];
                pixel[1] = 255 - pixel[1];
                pixel[2] = 255 - pixel[2];
                raster.setPixel(x, y, pixel);
            }
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtInvertHorizontal(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        int pixel[] = {0,0,0,0},  pixel2[] = {0,0,0,0}, x, y, z;
        WritableRaster raster = bimage.getRaster();
        BufferedImage bimageDestiny;
        bimageDestiny = new BufferedImage(bimage.getWidth(), bimage.getHeight(),bimage.getType());
        for(y=0; y<image.getHeight(); y++) 
            for(x=0, z=(int)image.getWidth()-1; x<image.getWidth()/2; x++, z--)
            {
                raster.getPixel(x, y, pixel);
                raster.getPixel(z, y, pixel2);
                raster.setPixel(x, y, pixel2);
                raster.setPixel(z, y, pixel);
            }
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtInvertVertical(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        int pixel[] = {0,0,0,0}, pixel2[] = {0,0,0,0}, x, y, z;
        WritableRaster raster = bimage.getRaster();
        BufferedImage bimageDestiny;
        bimageDestiny = new BufferedImage(bimage.getWidth(), bimage.getHeight(), bimage.getType());
        for(y=0, z=(int)image.getHeight()-1; y<image.getHeight()/2; y++, z--) 
            for(x = 0; x<image.getWidth(); x++)
            {
                raster.getPixel(x, y, pixel);
                raster.getPixel(x, z, pixel2);
                raster.setPixel(x, y, pixel2);
                raster.setPixel(x, z, pixel);
            }
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtErosao(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        imagePlus = new ImagePlus();
        imagePlus.setImage(bimage);
        ImageProcessor ip = imagePlus.getProcessor();
        ip.erode();
        imageView.setImage(SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null));
        bimage = imagePlus.getBufferedImage();
        image = SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtDetectarBorda(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        imagePlus = new ImagePlus();
        imagePlus.setImage(bimage);
        ImageProcessor ip = imagePlus.getProcessor();
        ip.findEdges();
        imageView.setImage(SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null));
        bimage = imagePlus.getBufferedImage();
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtNitidez(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        imagePlus = new ImagePlus();
        imagePlus.setImage(bimage);
        ImageProcessor ip = imagePlus.getProcessor();
        ip.smooth();
        imageView.setImage(SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null));
        bimage = imagePlus.getBufferedImage();
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }

    @FXML
    private void evtSuave(ActionEvent event) 
    {
        bimage = SwingFXUtils.fromFXImage(image, null);
        imagePlus = new ImagePlus();
        imagePlus.setImage(bimage);
        ImageProcessor ip = imagePlus.getProcessor();
        ip.sharpen();
        imageView.setImage(SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null));
        bimage = imagePlus.getBufferedImage();
        image = SwingFXUtils.toFXImage(bimage, null);
        imageView.setImage(image);
        imSalvar.setDisable(false);
        btSalvar.setDisable(false);
        flag = true;
    }
    
    @FXML
    private void evtAjuda(ActionEvent event) 
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("AppMyPhotoshop V1.0 ");
        alert.setContentText("Desenvolvido por\nRafael Damacena");
        alert.showAndWait(); 
    }
}
