package iida.rokka.harjoitustyo.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import iida.rokka.harjoitustyo.domain.CategoryRepository;
import iida.rokka.harjoitustyo.domain.Item;
import iida.rokka.harjoitustyo.domain.ItemRepository;

@Controller
public class KirppuController {
	@Autowired
	private ItemRepository repository;

	@Autowired
	private CategoryRepository catrepository;

	// aloitussivu
	@GetMapping("/")
	public String aloitus() {
		return "redirect:itemlist";
	}

	// Kirppis tuotteiden listaus
	@GetMapping("/itemlist")
	public String ItemList(Model model) {
		model.addAttribute("items", repository.findAll());
		model.addAttribute("likes", repository.findAll());
		return "itemlist";
	}

	// login
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	// Listaa tuotteet REST
	@GetMapping(value = "/items")
	public @ResponseBody List<Item> itemListRest() {
		return (List<Item>) repository.findAll();
	}

	// Listaa tuotteet id:n mukaan REST
	@GetMapping(value = "/item/{id}")
	public @ResponseBody Optional<Item> findItemRest(@PathVariable("id") Long ItemId) {
		return repository.findById(ItemId);
	}

	// Lisaa kirppistuote
	@RequestMapping(value = "/add")
	public String addItem(Model model) {
		model.addAttribute("item", new Item());
		model.addAttribute("categories", catrepository.findAll());
		return "additem";
	}

	// Tallentaa kirppistuotteen
	@PostMapping("/save")
	public String save(Item item) {
		item.setDate(LocalDate.now());
		repository.save(item);
		return "redirect:itemlist";
	}

	// Poistaa kirppistuotteen
	@GetMapping("/delete/{id}")
	public String deleteItem(@PathVariable("id") Long ItemId, Model model) {
		repository.deleteById(ItemId);
		return "redirect:../itemlist";
	}

	// Editoi kirppistuotteen tietoja
	@RequestMapping(value = "/edit/{id}")
	public String editItem(@PathVariable("id") Long ItemId, Model model) {
		model.addAttribute("item", repository.findById(ItemId));
		model.addAttribute("categories", catrepository.findAll());
		return "edititem";
	}

	// TESTI
	@GetMapping("/index")
	public String test() {
		return "itemlist";
	}

}
