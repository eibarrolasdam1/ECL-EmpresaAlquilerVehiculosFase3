
package alquileres.interfaz;

import java.awt.Button;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;

import org.graalvm.compiler.phases.common.NodeCounterPhase.Stage;

import alquileres.modelo.AgenciaAlquiler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class GuiAgenciaAlquiler extends Application
{

	private MenuItem itemLeer;
	private MenuItem itemGuardar;
	private MenuItem itemSalir;

	private TextArea area;

	private Button btnListarTodo;
	private TextField txtDias;
	private Button btnBuscar;

	private RadioButton rbtCoches;
	private RadioButton rbtFurgonetas;
	private Button btnMostrar;

	private AgenciaAlquiler agencia; // el modelo

	@Override
	public void start(Stage stage) {

		BorderPane root = crearGui();

		Scene scene = new Scene(root, 900, 500);
		stage.setScene(scene);
		stage.setTitle("- Alquiler de vehículos -");
		scene.getStylesheets().add(
		                getClass().getResource("application.css")
		                                .toExternalForm());
		stage.show();

		agencia = new AgenciaAlquiler("Rent-A-Car");
		cogerFoco();
	}

	private BorderPane crearGui() {

		BorderPane panel = new BorderPane();
		panel.setTop(crearBarraMenu());
		panel.setCenter(crearPanelPrincipal());
		return panel;
	}


	private BorderPane crearPanelPrincipal() {

		BorderPane panel = new BorderPane();
		area = new TextArea();
		panel.setCenter(area);

		VBox pnlBotones = crearPanelOperaciones();
		panel.setLeft(pnlBotones);

		return panel;
	}

	private VBox crearPanelOperaciones() {


		return null;
	}

	private MenuBar crearBarraMenu() {

		MenuBar barra = new MenuBar();




		return barra;
	}

	private void buscar() {


	}

	private void mostrar() {

		clear();

	}

	private void listarTodo() {

	}


	private void guardar() {

		clear();



	}

	private void leerFichero() {

		clear();


	}

	private void salir() {

	}


	private void clear() {

		area.clear();
	}

	private void cogerFoco() {

		txtDias.requestFocus();
		txtDias.selectAll();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
