package pl.edu.utp.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.utp.backend.entity.Firma;
import pl.edu.utp.backend.entity.Kontakt;
import pl.edu.utp.backend.service.FirmaService;
import pl.edu.utp.backend.service.KontaktService;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private KontaktService contactService;
    private Grid<Kontakt> grid = new Grid<>(Kontakt.class);
    private ContactForm form;

    private TextField filterText = new TextField();
    @Autowired
    public MainView(KontaktService contactService, FirmaService companyService) {

        this.contactService = contactService;
        addClassName("list-view");
        setSizeFull();
//        configureFilter();
        configureGrid();
        form = new ContactForm(companyService.findAll());
        form.addListener(ContactForm.SaveEvent.class, this::saveContact);
        form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();
        add(getToolbar(), content);
        updateList();
        closeEditor();

    }

    private void configureGrid(){
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("firma");
        grid.setColumns("imie", "nazwisko", "email", "status");
        grid.addColumn(contact -> {
            Firma company = contact.getFirma();
            return company == null ? "-" :company.getNazwa();
        }).setHeader("Firma");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editContact(event.getValue()));
    }

    private void configureFilter(){
        filterText.setPlaceholder("Filtrowanie po nazwie...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filtruj po nazwie...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Dodaj kontakt");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList(){
        grid.setItems(contactService.findAll(filterText.getValue()));
    }

    void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Kontakt());
    }

    public void editContact(Kontakt contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveContact(ContactForm.SaveEvent event) {
        contactService.save(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        contactService.delete(event.getContact());
        updateList();
        closeEditor();
    }

}
