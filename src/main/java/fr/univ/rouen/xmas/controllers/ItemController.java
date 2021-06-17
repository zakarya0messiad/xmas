package fr.univ.rouen.xmas.controllers;
import fr.univ.rouen.xmas.dto.ItemDTO;
import fr.univ.rouen.xmas.entities.Item;
import fr.univ.rouen.xmas.services.ItemService;
import fr.univ.rouen.xmas.utils.PdfExport;
import org.dom4j.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems(){
        return ResponseEntity.ok().body(itemService.getAllItems());
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable UUID id){
        return ResponseEntity.ok().body(itemService.getItemById(id));
    }

    @GetMapping("/items/user/{id}")
    public ResponseEntity<List<Item>> getItemsByUserId(@PathVariable UUID id){
        return ResponseEntity.ok().body(itemService.getItemsByUser(id));
    }

    @PostMapping("/items")
    public Item createItem(@RequestBody Item item){
        return itemService.createItem(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable UUID id, @RequestBody Item item){
        item.setId(id);
        return ResponseEntity.ok().body(this.itemService.updateItem(item));
    }

    @PutMapping("/items/{id}/finish")
    public ResponseEntity<Item> finishItem(@PathVariable UUID id){
        return ResponseEntity.ok().body(this.itemService.finishItem(id));
    }

    @DeleteMapping("/items/{id}")
    public HttpStatus deleteItem(@PathVariable UUID id){
        this.itemService.deleteItem(id);
        return HttpStatus.OK;
    }

    //Permet d'exporter la liste des commandes au format PDF"
    @GetMapping(path = "/items/exportpdf")
    public ResponseEntity<InputStreamResource> exportPDF() throws DocumentException, com.itextpdf.text.DocumentException {
        List<ItemDTO> itemDTO = new ArrayList<>();
        for (Item item : itemService.getAllItems()) {
            ItemDTO iDTO = modelMapper.map(item, ItemDTO.class);
            itemDTO.add(iDTO);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf("application/pdf"));
        httpHeaders.add("Content-Disposition", "attachment; filename=liste_des_utilisateurs" + ".pdf");

        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(PdfExport.itemsExport(itemDTO)));
    }

}
