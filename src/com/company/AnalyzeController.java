package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnalyzeController {

    @FXML
    private ImageView background;

    @FXML
    private TableView<Spen> spenTable;

    @FXML
    private TableColumn<Spen, String> idColumn;

    @FXML
    private TableColumn<Spen, String> spenColumn;

    @FXML
    private Label totalSpen;

    @FXML
    private Label fullPList;

    @FXML
    private Label fullMList;

    @FXML
    private Label fullCList;

    @FXML
    private Label fullTList;

    @FXML
    private Label ioPList;

    @FXML
    private Label ioMList;

    @FXML
    private Label ioCList;

    @FXML
    private Label ioTList;

    @FXML
    private Label fullMCount;

    @FXML
    private Label fullPCount;

    @FXML
    private Label fullCCount;

    @FXML
    private Label fullTCount;

    @FXML
    private Label ioPCount;

    @FXML
    private Label ioMCount;

    @FXML
    private Label ioCCount;

    @FXML
    private Label ioTCount;

    @FXML
    private Label fullResult;

    @FXML
    private Label ioResult;


    @FXML
    void initialize() {
        setSpenData();
        setChepinData();
        Image stena = new Image(getClass().getResource("/com/company/pic/stena.jpg").toExternalForm());
        background.setImage(stena);
    }

    private void setSpenData() {
        for (Spen s : Metrics.spenList) {
            s.setIdWrap(s.identifier);
            s.setCountWrap(s.count);
        }
        totalSpen.setText(Integer.toString(Metrics.totalSpen));

        spenTable.setItems(Metrics.spenList);

        idColumn.setCellValueFactory(cellData -> cellData.getValue().getIdWrap());
        spenColumn.setCellValueFactory(cellData -> cellData.getValue().getCountWrap());
    }

    private void setChepinData() {
        String full = "";
        String io = "";
        int[] counts = new int[8];
        for (Chepin.Context c : Metrics.pList) {
            if (!c.isIO) {
                full = full.concat(c.identifier + "\n");
                counts[0]++;
            }
            else {
                io = io.concat(c.identifier + "\n");
                counts[1]++;
            }
        }
        fullPList.setText(full);
        ioPList.setText(io);
        full = "";
        io = "";
        for (Chepin.Context c : Metrics.mList) {
            if (!c.isIO) {
                full = full.concat(c.identifier + "\n");
                counts[2]++;
            }
            else {
                counts[3]++;
                io = io.concat(c.identifier + "\n");
            }
        }
        fullMList.setText(full);
        ioMList.setText(io);
        full = "";
        io = "";
        for (Chepin.Context c : Metrics.cList) {
            if (!c.isIO) {
                counts[4]++;
                full = full.concat(c.identifier + "\n");
            }
            else {
                counts[5]++;
                io = io.concat(c.identifier + "\n");
            }
        }
        fullCList.setText(full);
        ioCList.setText(io);
        full = "";
        io = "";
        for (Chepin.Context c : Metrics.tList) {
            if (!c.isIO) {
                full = full.concat(c.identifier + "\n");
                counts[6]++;
            }
            else {
                io = io.concat(c.identifier + "\n");
                counts[7]++;
            }
        }
        fullTList.setText(full);
        ioTList.setText(io);

        fullPCount.setText(Integer.toString(counts[0]));
        ioPCount.setText(Integer.toString(counts[1]));
        fullMCount.setText(Integer.toString(counts[2]));
        ioMCount.setText(Integer.toString(counts[3]));
        fullCCount.setText(Integer.toString(counts[4]));
        ioCCount.setText(Integer.toString(counts[5]));
        fullTCount.setText(Integer.toString(counts[6]));
        ioTCount.setText(Integer.toString(counts[7]));

        fullResult.setText("Q = " + (counts[0] + counts[2] * 2 + counts[4] * 3 + counts[6] * 0.5));
        ioResult.setText("Q = " + (counts[1] + counts[3] * 2 + counts[5] * 3 + counts[7] * 0.5));
    }

}
