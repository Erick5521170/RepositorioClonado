package mcuca;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import mcuca.cliente.ClienteView;
import mcuca.establecimiento.EstablecimientoView;
import mcuca.usuario.UsuarioManagementView;
import mcuca.usuario.UsuarioView;


@SuppressWarnings("serial")
@SpringViewDisplay
public class MainScreen extends VerticalLayout implements ViewDisplay {

	private Panel springViewDisplay;

	@Override
    public void attach() {
        super.attach();
        this.getUI().getNavigator().navigateTo("");
    }

	@PostConstruct
	void init() {

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();

		// Creamos la cabecera
		Label titulo = new Label("McUCA");
		titulo.setStyleName("h1");
		root.addComponent(titulo);
		root.setComponentAlignment(titulo, Alignment.MIDDLE_CENTER);

		Button logoutButton = new Button("Salir", event -> logout());
		logoutButton.setStyleName(ValoTheme.BUTTON_LINK);
		root.addComponent(logoutButton);

		// Creamos la barra de navegación
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("C", ClienteView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("E", EstablecimientoView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Users", UsuarioView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Usuario Management", UsuarioManagementView.VIEW_NAME));
		root.addComponent(navigationBar);

		// Creamos el panel
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);

		addComponent(root);

	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		// If you didn't choose Java 8 when creating the project, convert this
		// to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}


	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component) view);
	}


	private void logout() {
		getUI().getPage().reload();
		getSession().close();
	}
}