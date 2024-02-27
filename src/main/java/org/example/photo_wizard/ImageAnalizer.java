package org.example.photo_wizard;

import org.example.photo_wizard.commons.Image;
import org.example.photo_wizard.commons.Information;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.photo_wizard.pdi.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ImageAnalizer extends Application {

    private BufferedImage reader;
    private BufferedImage modificada;
    private BorderPane pane;
    private ImageView original;
    private ImageView modify;
    private LineChart lineChart;
    private TextField textAverage;
    private TextField textMedian;
    private TextField textMode;
    private TextField textVariance;

    @Override
    public void start(Stage stageA) {
        initComponents();
        stageA.setMaximized(true);
        stageA.setScene(new Scene(pane));
        stageA.show();
    }

    /**
     * Method responsible for loading the imagem information
     *
     * @param file Image file
     */
    private void loadImage(File file) {
        try {
            reader = ImageIO.read(file);
            Image image = new Image(reader);
            original.setImage(SwingFXUtils.toFXImage(reader, null));
            AlteraImagem(image);
            laodStatistics(image);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Method responsible for initializing the components
     */
    private void initComponents() {
        pane = new BorderPane();
        pane.setPrefSize(990, 700);
        initComponentsMenu();
        initComponentsBoxImage();
        initComponentsBoxInfo();
    }

    /**
     * Method responsible for creating the menu
     */
    private void initComponentsMenu() {
        // Create menu
        MenuBar menuBar = new MenuBar();
        // File menu
        Menu file = new Menu("Arquivo");
        MenuItem fileOpen = new MenuItem("Novo");
        fileOpen.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            loadImage(fileChooser.showOpenDialog(null));
        });
        file.getItems().addAll(fileOpen);
        
        //Informações
        Menu informacoes = new Menu("Informações");
        MenuItem informacoesA = new MenuItem("Medias");
        informacoesA.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Médias da imagem");
            alert.setHeaderText(null);
            alert.setContentText(new Information(new Image(reader)).getInfoMedia());
            alert.showAndWait();
        });
        MenuItem informacoesB = new MenuItem("Medianas");
        informacoesB.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Medianas da imagem");
            alert.setHeaderText(null);
            alert.setContentText(new Information(new Image(reader)).getInfoMediana());
            alert.showAndWait();
        });
        MenuItem informacoesC = new MenuItem("Modas");
        informacoesC.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Modas da imagem");
            alert.setHeaderText(null);
            alert.setContentText(new Information(new Image(reader)).getInfoModa());
            alert.showAndWait();
        });
        MenuItem informacoesD = new MenuItem("Variâncias");
        informacoesD.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Variâncias da imagem");
            alert.setHeaderText(null);
            alert.setContentText(new Information(new Image(reader)).getInfoVariancia());
            alert.showAndWait();
        });
        MenuItem informacoesE = new MenuItem("Histograma primeiro quadrante");
        informacoesE.setOnAction((ActionEvent event) -> loadHistogramer(new Histograma(new Image(reader)).getHistogramaQuadrante(1)));
        MenuItem informacoesF = new MenuItem("Qtd.pixels < 100 na metade superior");
        informacoesF.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Qtd.pixels < 100 na metade superior");
            alert.setHeaderText(null);
            alert.setContentText("Quantidade: " + new Information(new Image(reader)).qtdPixelMenorSuperior(100));
            alert.showAndWait();
        });
        MenuItem informacoesG = new MenuItem("Qtd.pixels > 150 na metade inferior");
        informacoesG.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Qtd.pixels > 150 na metade inferior");
            alert.setHeaderText(null);
            alert.setContentText("Quantidade: " + new Information(new Image(reader)).qtdPixelMaiorInferior(150));
            alert.showAndWait();
        });
        informacoes.getItems().addAll(informacoesA, informacoesB, informacoesC, informacoesD, informacoesE, informacoesF, informacoesG);
        
        // Statictics
        Menu alteracoes = new Menu("Alterações");
        MenuItem alteracoesA = new MenuItem("Valores maiores ou iguais a média do quadrante 2 recebem branco");
        alteracoesA.setOnAction((ActionEvent event) -> AlteraImagem(new Alteracoes(new Image(reader)).setBrancoMaiorMediaQuadrante(2)));
        MenuItem alteracoesB = new MenuItem("Valores maiores ou iguais a moda do quadrante 4 recebem 200");
        alteracoesB.setOnAction((ActionEvent event) -> AlteraImagem(new Alteracoes(new Image(reader)).setValorMaiorModaQuadrante(4, 200)));
        MenuItem alteracoesC = new MenuItem("Valores maiores ou iguais a mediana do quadrante 3 recebem 220");
        alteracoesC.setOnAction((ActionEvent event) -> AlteraImagem(new Alteracoes(new Image(reader)).setValorMaiorMedianaQuadrante(3, 220)));
        MenuItem alteracoesD = new MenuItem("Valores menores que a média do quadrante 2 recebem 100");
        alteracoesD.setOnAction((ActionEvent event) -> AlteraImagem(new Alteracoes(new Image(reader)).setValorMenorMediaQuadrante(2,100)));
        MenuItem alteracoesE = new MenuItem("Valores maiores que a média do quadrante 2 recebem 0 e menores que a mediana do quadrante 3 recebem 255");
        alteracoesE.setOnAction((ActionEvent event) -> {
            Image imagem = new Alteracoes(new Image(reader)).setValorMaiorMediaQuadrante(2,0);
            AlteraImagem(new Alteracoes(imagem).setValorMenorMedianaQuadrante(3,255));
        });
        alteracoes.getItems().addAll(alteracoesA, alteracoesB, alteracoesC, alteracoesD, alteracoesE);
        // Transformações
        Menu transformacao = new Menu("Transformações");
        MenuItem transformacaoRotacao = new MenuItem("Rotação");
        transformacaoRotacao.setOnAction((ActionEvent event) -> {
        	TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("Ângulo:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(angulo -> AlteraImagem(new Transformacoes(new Image(reader)).rotacaoAngulo(Integer.parseInt(angulo))));
        });
        MenuItem transformacaoRotacao2 = new MenuItem("Rotação deslizante");
        transformacaoRotacao2.setOnAction((ActionEvent event) -> executaRotacao());
        MenuItem transformacaoAmpliacao = new MenuItem("Ampliação");
        transformacaoAmpliacao.setOnAction((ActionEvent event) -> {
        	TextInputDialog dialog = new TextInputDialog("2");
            dialog.setContentText("Fator:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(fator -> AlteraImagem(new Transformacoes(new Image(reader)).amplia(Double.parseDouble(fator))));
        });
        MenuItem transformacaoReducao = new MenuItem("Redução");
        transformacaoReducao.setOnAction((ActionEvent event) -> {
        	TextInputDialog dialog = new TextInputDialog("2");
            dialog.setContentText("Fator:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(fator -> AlteraImagem(new Transformacoes(new Image(reader)).reduz(Double.parseDouble(fator))));
        });
        Menu transformacaoEspalhamento = new Menu("Espelhamento");
        MenuItem transformacaoEspalhamentoHor = new MenuItem("Horizontal");
        transformacaoEspalhamentoHor.setOnAction((ActionEvent event) -> AlteraImagem(new Transformacoes(new Image(reader)).espelhaHorizontal()));
        MenuItem transformacaoEspalhamentoVer = new MenuItem("Vertical");
        transformacaoEspalhamentoVer.setOnAction((ActionEvent event) -> AlteraImagem(new Transformacoes(new Image(reader)).espelhaVertical()));
        transformacaoEspalhamento.getItems().addAll(transformacaoEspalhamentoHor, transformacaoEspalhamentoVer);
        //monta itens
        transformacao.getItems().addAll(transformacaoRotacao, transformacaoRotacao2, transformacaoAmpliacao,
                transformacaoReducao, transformacaoEspalhamento);
        // Filtros
        Menu filtro = new Menu("Filtros (PB)");
        MenuItem filtroMediana = new MenuItem("Mediana");
        filtroMediana.setOnAction((ActionEvent event) -> AlteraImagem(new Filtros(new Image(reader)).filtroMediana()));
        MenuItem filtroGauss = new MenuItem("Gauss");
        filtroGauss.setOnAction((ActionEvent event) -> AlteraImagem(new Filtros(new Image(reader)).filtroGauss()));
        MenuItem filtroLimirializacao = new MenuItem("Limirialização");
        filtroLimirializacao.setOnAction((ActionEvent event) -> executaThreshold());
        filtro.getItems().addAll(filtroMediana, filtroGauss, filtroLimirializacao);
        //Detecção de borda
        Menu filtroDeteccaoBordas = new Menu("Detecção de borda (PA)");
        MenuItem prewitt = new MenuItem("Prewitt");
        prewitt.setOnAction((ActionEvent event) -> executeDeteccaoPrewitt());
        MenuItem laplaciano = new MenuItem("Laplaciano");
        laplaciano.setOnAction((ActionEvent event) -> executeDeteccaoLaplaciano());
        MenuItem canny = new MenuItem("Canny");
        canny.setOnAction((ActionEvent event) -> executeDeteccaoCanny());
        filtroDeteccaoBordas.getItems().addAll(prewitt, laplaciano, canny);
        // Morfologia 
        Menu morfologia = new Menu("Morfologia");
        MenuItem morfologiaDilatacao = new MenuItem("Dilatação");
        morfologiaDilatacao.setOnAction((ActionEvent event) -> AlteraImagem(new Morfologia(new Image(modificada)).dilatacao()));
        MenuItem morfologiaErosao = new MenuItem("Erosão");
        morfologiaErosao.setOnAction((ActionEvent event) -> AlteraImagem(new Morfologia(new Image(modificada)).erosao()));
        MenuItem morfologiaAfinamento = new MenuItem("Afinamento");
        morfologiaAfinamento.setOnAction((ActionEvent event) -> AlteraImagem(new Morfologia(new Alteracoes(new Image(reader)).threshold(150)).AfinamentoHolt()));
        MenuItem morfologiaAbertura = new MenuItem("Abertura");
        morfologiaAbertura.setOnAction((ActionEvent event) -> AlteraImagem(new Morfologia(new Morfologia(new Image(reader)).erosao()).dilatacao()));
        MenuItem morfologiaFechamento = new MenuItem("Fechamento");
        morfologiaFechamento.setOnAction((ActionEvent event) -> AlteraImagem(new Morfologia(new Morfologia(new Image(reader)).dilatacao()).erosao()));
        morfologia.getItems().addAll(morfologiaDilatacao, morfologiaErosao, morfologiaAfinamento, morfologiaAbertura, morfologiaFechamento );
        // Extração de caracteristica
        Menu extracao = new Menu("Extração caracteristica");
        MenuItem extracaoQuadrado = new MenuItem("1 Quadrado");
        extracaoQuadrado.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(new Extrator(new Image(reader)).extraiQuadrado());
            alert.showAndWait();
        });
        MenuItem extracaoMenorCirculo = new MenuItem("Maior círculo");
        extracaoMenorCirculo.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(new Extrator(new Image(reader)).extraiMaiorCirculo());
            alert.showAndWait();
        });
        MenuItem extracaoTodosCirculo = new MenuItem("Todos círculos");
        extracaoTodosCirculo.setOnAction((ActionEvent event) -> {
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Informações");
            alert.setHeaderText(null);
            alert.setContentText(new Extrator(new Image(reader)).extraiCirculos());
            alert.showAndWait();
        });
        extracao.getItems().addAll(extracaoQuadrado, extracaoMenorCirculo, extracaoTodosCirculo);
        // Add menu itens
        menuBar.getMenus().addAll(file,informacoes, alteracoes, transformacao, filtro, filtroDeteccaoBordas, morfologia, extracao);
        pane.setTop(menuBar);
    }

    /**
     * Executa o processo de Threshold
     */
    private void executaThreshold() {
        ControleDeslizante th = new ControleDeslizante(0,255);
        th.addObserver((threshold) -> AlteraImagem(new Alteracoes(new Image(reader)).threshold(threshold)));
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }
    private void executeDeteccaoPrewitt(){
    	 ControleDeslizante th = new ControleDeslizante(0,255);
         th.addObserver((threshold) -> AlteraImagem(new Filtros(new Image(reader)).deteccaoPrewitt(threshold)));
         th.setValue(150);
         Dialog<Double[]> dialog = new Dialog<>();
         dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
         dialog.setTitle("Thresholding");
         dialog.getDialogPane().setContent(th);
         dialog.show();
    }
    
    private void executeDeteccaoCanny(){
   	 ControleDeslizante th = new ControleDeslizante(0,255);
        th.addObserver((threshold) -> AlteraImagem(new Filtros(new Image(reader)).deteccaoCanny(threshold)));
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
   }
    
    private void executeDeteccaoLaplaciano(){
   	 ControleDeslizante th = new ControleDeslizante(0,255);
        th.addObserver((threshold) -> AlteraImagem(new Filtros(new Image(reader)).deteccaoLaplaciano(threshold)));
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
   }
    
    /**
     * Executa o processo de rotação
     */
    private void executaRotacao() {
        ControleDeslizante th = new ControleDeslizante(0,360);
        th.addObserver((angulo) -> AlteraImagem(new Transformacoes(new Image(reader)).rotacaoAngulo(angulo)));
        th.setValue(0);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    /**
     * Method responsible for creating the imagens boxes
     */
    private void initComponentsBoxImage() {
        GridPane boxImage = new GridPane();
        boxImage.setPadding(new Insets(0, 10, 0, 10));
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxImage.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create original image
        original = new ImageView();
        original.setFitHeight(465);
        original.setFitWidth(620);
        original.setPreserveRatio(true);
        BorderPane boxImageLeft = new BorderPane();
        boxImageLeft.setPadding(new Insets(10));
        BorderPane boxImageLeftInner = new BorderPane();
        boxImageLeftInner.setCenter(original);
        boxImageLeftInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        BorderPane titleOriginal = new BorderPane();
        titleOriginal.setLeft(createTitle("Imagem original"));
        boxImageLeft.setTop(titleOriginal);
        boxImageLeft.setCenter(boxImageLeftInner);
        // Create modifyed imagem
        modify = new ImageView();
        modify.setFitHeight(465);
        modify.setFitWidth(620);
        modify.setPreserveRatio(true);
        BorderPane boxImageRight = new BorderPane();
        boxImageRight.setPrefHeight(465);
        boxImageRight.setPadding(new Insets(10));
        BorderPane boxImageRightInner = new BorderPane();
        boxImageRightInner.setCenter(modify);
        boxImageRightInner.setStyle("-fx-background-color:#EEEEEE; -fx-border-color: #CCC; -fx-border:1px;");
        boxImageRight.setTop(createTitle("Imagem modificada"));
        boxImageRight.setCenter(boxImageRightInner);
        // Add imagens in the grid
        boxImage.addColumn(0, boxImageLeft);
        boxImage.addColumn(1, boxImageRight);
        pane.setCenter(boxImage);
    }

    /**
     * Method resposible for creating de info box
     */
    private void initComponentsBoxInfo() {
        GridPane boxInfo = new GridPane();
        boxInfo.setPadding(new Insets(10));
        boxInfo.setStyle("-fx-border-color: #999; -fx-border-top: 1px;");
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxInfo.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create chart box
        BorderPane paneChart = new BorderPane();
        lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setPrefHeight(80);
        lineChart.setAnimated(false);
        paneChart.setCenter(lineChart);
        BorderPane boxInfoLeft = new BorderPane();
        boxInfoLeft.setCenter(paneChart);
        // Create info box
        BorderPane boxInfoRight = new BorderPane();
        boxInfoRight.setPrefHeight(150);
        boxInfoRight.setPadding(new Insets(10));
        boxInfoRight.setCenter(initComponentsBoxInfoStatistics());
        // Add imagens in the grid
        boxInfo.addColumn(0, boxInfoLeft);
        boxInfo.addColumn(1, boxInfoRight);
        pane.setBottom(boxInfo);
    }

    /**
     * Method responsible for creating the statics box
     *
     * @return GridPane
     */
    private GridPane initComponentsBoxInfoStatistics() {
        GridPane boxInfo = new GridPane();
        // Set constraints
        ColumnConstraints comConstraints = new ColumnConstraints();
        comConstraints.setPercentWidth(50);
        boxInfo.getColumnConstraints().addAll(comConstraints, comConstraints);
        // Create the average field
        BorderPane averageBox = new BorderPane();
        averageBox.setPadding(new Insets(0, 5, 10, 0));
        Label averageLabel = new Label("Média:");
        textAverage = new TextField();
        textAverage.setDisable(true);
        averageBox.setTop(averageLabel);
        averageBox.setCenter(textAverage);
        boxInfo.addColumn(0, averageBox);
        // Create the median field
        BorderPane medianBox = new BorderPane();
        medianBox.setPadding(new Insets(0, 0, 10, 5));
        Label mediaLabel = new Label("Mediana:");
        textMedian = new TextField();
        textMedian.setDisable(true);
        medianBox.setTop(mediaLabel);
        medianBox.setCenter(textMedian);
        boxInfo.addColumn(1, medianBox);
        // Create the mode field
        BorderPane modeBox = new BorderPane();
        modeBox.setPadding(new Insets(0, 5, 0, 0));
        Label modeLabel = new Label("Moda:");
        textMode = new TextField();
        textMode.setDisable(true);
        modeBox.setTop(modeLabel);
        modeBox.setCenter(textMode);
        boxInfo.addColumn(0, modeBox);
        // Create the variance field
        BorderPane varianceBox = new BorderPane();
        varianceBox.setPadding(new Insets(0, 0, 0, 5));
        Label varianceLabel = new Label("Variância:");
        textVariance = new TextField();
        textVariance.setDisable(true);
        varianceBox.setTop(varianceLabel);
        varianceBox.setCenter(textVariance);
        boxInfo.addColumn(1, varianceBox);
        return boxInfo;
    }

    /**
     * Method resposible for creating a histogram chart
     *
     * @param histogram Histogram data
     */
    private void loadHistogramer(int[] histogram) {
        Platform.runLater(() -> {
            XYChart.Series nodo = new XYChart.Series();
            int c = 0;
            for (int obj : histogram) {
                nodo.getData().add(new XYChart.Data(String.valueOf(++c), obj));
            }
            lineChart.getData().clear();
            lineChart.getData().addAll(nodo);
        });
    }

    /**
     * Method responsible for loading the image statistics
     *
     * @param imagem Image information
     */
    private void laodStatistics(Image imagem) {
        int[] histogram = new Histograma(imagem).getHistograma();
        Information info = new Information(new Image(reader));
        int average = info.getMedia();
        int median = info.getMediana();
        int variace = info.getVariancia();
        int mode = info.getModa();
        loadHistogramer(histogram);
        Platform.runLater(() -> {
            textAverage.setText(String.valueOf(average));
            textMedian.setText(String.valueOf(median));
            textVariance.setText(String.valueOf(variace));
            textMode.setText(String.valueOf(mode));
        });
    }

    /**
     * Method responsible for creating titles
     *
     * @param title The title
     * @return Label
     */
    private Label createTitle(String title) {
        return createTitle(title, 16);
    }

    /**
     * Method responsible for creating titles
     *
     * @param title The title
     * @param size Font Size
     * @return Label
     */
    private Label createTitle(String title, int size) {
        Label label = new Label(title);
        label.setFont(new Font("Arial", size));
        label.setTextFill(Color.web("#666"));
        label.setPadding(new Insets(0, 0, 5, 0));
        return label;
    }
    
    private void AlteraImagem(Image imagem){
    	modificada = imagem.getBufferdImage();
    	modify.setImage(SwingFXUtils.toFXImage(modificada, null));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
