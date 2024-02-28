package org.example.photo_wizard;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.example.photo_wizard.commons.Image;
import org.example.photo_wizard.commons.Information;
import org.example.photo_wizard.pdi.*;

import java.io.File;
import java.util.Optional;

public class MainViewController {

    @FXML
    private TextField avarageText;

    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private TextField medianText;

    @FXML
    private TextField modeText;

    @FXML
    private ImageView modifiedImageView;

    @FXML
    private ImageView originalImageView;

    @FXML
    private TextField varianceText;

    private Image originalImage;
    
    private Image modifiedImage;

    private final MainViewService mainViewService = new MainViewService();

    @FXML
    void onAlteracaoAClick(ActionEvent event) {
        modifiedImage = new Alteracoes(new Image(originalImage)).setBrancoMaiorMediaQuadrante(2);
        alteraImagem();
    }

    @FXML
    void onAlteracaoBClick(ActionEvent event) {
        modifiedImage = new Alteracoes(new Image(originalImage)).setValorMaiorModaQuadrante(4, 200);
        alteraImagem();
    }

    @FXML
    void onAlteracaoCClick(ActionEvent event) {
        modifiedImage = new Alteracoes(new Image(originalImage)).setValorMaiorMedianaQuadrante(3, 220);
        alteraImagem();
    }

    @FXML
    void onAlteracaoDClick(ActionEvent event) {
        modifiedImage = new Alteracoes(new Image(originalImage)).setValorMenorMediaQuadrante(2, 100);
        alteraImagem();
    }

    @FXML
    void onAlteracaoEClick(ActionEvent event) {
        Image imagem = new Alteracoes(new Image(originalImage)).setValorMaiorMediaQuadrante(2, 0);
        modifiedImage = new Alteracoes(imagem).setValorMenorMedianaQuadrante(3, 255);
        alteraImagem();
    }

    @FXML
    void onCannyClick(ActionEvent event) {
        //executeDeteccaoCanny();

        ControleDeslizante th = new ControleDeslizante(0, 255);
        th.addObserver((threshold) -> {
            modifiedImage = new Filtros(new Image(originalImage)).deteccaoCanny(threshold);
            alteraImagem();
        });
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    @FXML
    void onExtracaoMaiorCirculoClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informações");
        alert.setHeaderText(null);
        alert.setContentText(new Extrator(new Image(originalImage)).extraiMaiorCirculo());
        alert.showAndWait();
    }

    @FXML
    void onExtracaoQuadradoClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informações");
        alert.setHeaderText(null);
        alert.setContentText(new Extrator(new Image(originalImage)).extraiQuadrado());
        alert.showAndWait();
    }

    @FXML
    void onExtracaoTodosCirculosClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informações");
        alert.setHeaderText(null);
        alert.setContentText(new Extrator(new Image(originalImage)).extraiCirculos());
        alert.showAndWait();
    }

    @FXML
    void onFileOpenClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        originalImage = mainViewService.loadImage(file);
        originalImageView.setImage(SwingFXUtils.toFXImage(originalImage.getBufferdImage(), null));
        modifiedImage = originalImage;
        alteraImagem();
        laodStatistics();
    }

    @FXML
    void onFiltroGaussClick(ActionEvent event) {
        modifiedImage = new Filtros(new Image(originalImage)).filtroGauss();
        alteraImagem();
    }

    @FXML
    void onFiltroLimirializacaoClick(ActionEvent event) {
        //executaThreshold();

        ControleDeslizante th = new ControleDeslizante(0, 255);
        th.addObserver((threshold) -> {
            modifiedImage = new Alteracoes(new Image(originalImage)).threshold(threshold);
            alteraImagem();
        });
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    @FXML
    void onFiltroMedianaClick(ActionEvent event) {
        modifiedImage = new Filtros(new Image(originalImage)).filtroMediana();
        alteraImagem();
    }

    @FXML
    void onInfoHistogramaPrimeiroQuadranteClick(ActionEvent event) {
        loadHistogramer(new Histograma(new Image(originalImage)).getHistogramaQuadrante(1));
    }

    @FXML
    void onInfoMedianasClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Medianas da imagem");
        alert.setHeaderText(null);
        alert.setContentText(new Information(new Image(originalImage)).getInfoMediana());
        alert.showAndWait();
    }

    @FXML
    void onInfoMediasClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Médias da imagem");
        alert.setHeaderText(null);
        alert.setContentText(new Information(new Image(originalImage)).getInfoMedia());
        alert.showAndWait();
    }

    @FXML
    void onInfoModasClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modas da imagem");
        alert.setHeaderText(null);
        alert.setContentText(new Information(new Image(originalImage)).getInfoModa());
        alert.showAndWait();
    }

    @FXML
    void onInfoQtdPixelsLess100MetadeSuperiorClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Qtd.pixels < 100 na metade superior");
        alert.setHeaderText(null);
        alert.setContentText("Quantidade: " + new Information(new Image(originalImage)).qtdPixelMenorSuperior(100));
        alert.showAndWait();
    }

    @FXML
    void onInfoQtdPixlesGreater150MetadeInferiorClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Qtd.pixels > 150 na metade inferior");
        alert.setHeaderText(null);
        alert.setContentText("Quantidade: " + new Information(new Image(originalImage)).qtdPixelMaiorInferior(150));
        alert.showAndWait();
    }

    @FXML
    void onInfoVarianciasClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Variâncias da imagem");
        alert.setHeaderText(null);
        alert.setContentText(new Information(new Image(originalImage)).getInfoVariancia());
        alert.showAndWait();
    }

    @FXML
    void onLaplacianoClick(ActionEvent event) {
        //executeDeteccaoLaplaciano();


        ControleDeslizante th = new ControleDeslizante(0, 255);
        th.addObserver((threshold) -> {
            modifiedImage = new Filtros(new Image(originalImage)).deteccaoLaplaciano(threshold);
            alteraImagem();
        });
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    @FXML
    void onMorfologiaAberturaClick(ActionEvent event) {
        modifiedImage = new Morfologia(new Morfologia(new Image(originalImage)).erosao()).dilatacao();
        alteraImagem();
    }

    @FXML
    void onMorfologiaAfinamentoClick(ActionEvent event) {
        modifiedImage = new Morfologia(new Alteracoes(new Image(originalImage)).threshold(150)).AfinamentoHolt();
        alteraImagem();

    }

    @FXML
    void onMorfologiaDilatacaoClick(ActionEvent event) {
        modifiedImage = new Morfologia(new Image(modifiedImage)).dilatacao();
        alteraImagem();
    }

    @FXML
    void onMorfologiaErosaoClick(ActionEvent event) {
        modifiedImage = new Morfologia(new Image(modifiedImage)).erosao();
        alteraImagem();
    }

    @FXML
    void onMorfologiaFechamentoClick(ActionEvent event) {
        modifiedImage = new Morfologia(new Morfologia(new Image(originalImage)).dilatacao()).erosao();
        alteraImagem();
    }

    @FXML
    void onPrewittClick(ActionEvent event) {
        //executeDeteccaoPrewitt();

        ControleDeslizante th = new ControleDeslizante(0, 255);
        th.addObserver((threshold) -> {
            modifiedImage = new Filtros(new Image(originalImage)).deteccaoPrewitt(threshold);
            alteraImagem();
        });
        th.setValue(150);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    @FXML
    void onTransformacaoAmpliacaoClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("2");
        dialog.setContentText("Fator:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String fator = result.get();
            modifiedImage = new Transformacoes(new Image(originalImage)).amplia(Double.parseDouble(fator));
            alteraImagem();
        }
    }

    @FXML
    void onTransformacaoEspalhamentoHorClick(ActionEvent event) {
        modifiedImage = new Transformacoes(new Image(originalImage)).espelhaHorizontal();
        alteraImagem();
    }

    @FXML
    void onTransformacaoEspalhamentoVerClick(ActionEvent event) {
        modifiedImage = new Transformacoes(new Image(originalImage)).espelhaVertical();
        alteraImagem();
    }

    @FXML
    void onTransformacaoReducaoClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("2");
        dialog.setContentText("Fator:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String fator = result.get();
            modifiedImage = new Transformacoes(new Image(originalImage)).reduz(Double.parseDouble(fator));
            alteraImagem();
        }
    }

    @FXML
    void onTransformacaoRotacaoClick(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Ângulo:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String angulo = result.get();
            modifiedImage = new Transformacoes(new Image(originalImage)).rotacaoAngulo(Integer.parseInt(angulo));
            alteraImagem();
        }
    }

    @FXML
    void onTransformacaoRotacaoDeslizanteClick(ActionEvent event) {

        //executaRotacao()
        ControleDeslizante th = new ControleDeslizante(0, 360);
        th.addObserver((angulo) -> {
            modifiedImage = new Transformacoes(new Image(originalImage)).rotacaoAngulo(angulo);
            alteraImagem();
        });
        th.setValue(0);
        Dialog<Double[]> dialog = new Dialog<>();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.setTitle("Thresholding");
        dialog.getDialogPane().setContent(th);
        dialog.show();
    }

    private void alteraImagem() {
        modifiedImageView.setImage(SwingFXUtils.toFXImage(modifiedImage.getBufferdImage(), null));
    }

    /**
     * Method responsible for loading the image statistics
     */
    private void laodStatistics() {
        int[] histogram = new Histograma(originalImage).getHistograma();
        Information info = new Information(new Image(originalImage));
        int average = info.getMedia();
        int median = info.getMediana();
        int variace = info.getVariancia();
        int mode = info.getModa();
        loadHistogramer(histogram);
        Platform.runLater(() -> {
            avarageText.setText(String.valueOf(average));
            medianText.setText(String.valueOf(median));
            varianceText.setText(String.valueOf(variace));
            modeText.setText(String.valueOf(mode));
        });
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

}
