package iida.rokka.harjoitustyo.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import iida.rokka.harjoitustyo.domain.CategoryRepository;
import iida.rokka.harjoitustyo.domain.Item;
import iida.rokka.harjoitustyo.domain.ItemRepository;
import iida.rokka.harjoitustyo.domain.User;
import iida.rokka.harjoitustyo.domain.UserRepository;

@Controller
public class KirppuController {

	@Autowired
	private ItemRepository repository;

	@Autowired
	private CategoryRepository catrepository;

	@Autowired
	private UserRepository urepository;

	// aloitussivu, vie itemlist sivulle
	@GetMapping("/")
	public String aloitus() {
		return "redirect:itemlist";
	}

	// Lista ja sen järjestäminen
	public List<Item> Sorting(String method) {

		List<Item> Sorting = repository.findAll();

		// Custom comparatorit, aakkoset comparator Item luokassa
		Comparator<Item> compareByDate = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				return i1.getDate().compareTo(i2.getDate());
			}
		};

		Comparator<Item> compareByPrice = new Comparator<Item>() {
			@Override
			public int compare(Item i1, Item i2) {
				return i1.getPrice().compareTo(i2.getPrice());
			}
		};

		if (method.equals("alpha")) {
			Collections.sort(Sorting);
		} else if (method.equals("date")) {
			Collections.sort(Sorting, compareByDate);
		} else if (method.equals("price")) {
			Collections.sort(Sorting, compareByPrice);
		} else if (method.equals("default")) {
			// ei tee mitään
		}
		return Sorting;
	}

	// Itemlist ilman sorttia
	@GetMapping("/itemlist")
	public String ItemList(Model model) {

		// Usernamen etsiminen
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "admin";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// Tykkäystoiminto testiprintti
		List<Item> item1 = repository.findByName("Sohva");
		Item Item2 = item1.get(0);
		System.out.print(Item2.getLikers());

		model.addAttribute("items", repository.findAll());
		model.addAttribute("currUser", username);
		return "itemlist";
	}

	// Itemlist sivu, jossa Kirppistuotteiden listaus, tykkäys, lisääminen,
	// poistaminen ja editointi (Sortin kanssa)
	@GetMapping("/itemlist/{sort}")
	public String ItemListSort(@PathVariable("sort") String sort, Model model) {

		// Usernamen haku
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "admin";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		model.addAttribute("currUser", username);
		model.addAttribute("items", Sorting(sort));
		return "itemlist";
	}

	// login
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	// Registration / tilin luonti
	@GetMapping("/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		User userDto = new User();
		model.addAttribute("user", userDto);
		return "registration";
	}

	// Listaa tuotteet REST
	@GetMapping(value = "/items")
	public @ResponseBody List<Item> itemListRest() {
		return (List<Item>) repository.findAll();
	}

	// Listaa tuotteet id:n mukaan REST
	@GetMapping(value = "/items/{id}")
	public @ResponseBody Optional<Item> findItemRest(@PathVariable("id") Long ItemId) {
		return repository.findById(ItemId);
	}

	// Tykkäystoiminto
	@GetMapping("/like/{id}")
	public String like(@PathVariable("id") Long ItemId) {

		// Tykättävän itemin haku id:n perusteella
		Optional<Item> OptionalItem = repository.findById(ItemId);
		Item Item = OptionalItem.get();

		// Usernamen haku
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		// likers listan haku
		ArrayList<String> List1 = Item.getLikers();

		// Jos lista on tyhjä, lisää usernamen heti. Jos ei, tarkistaa onko username
		// listassa ja lisää vain jos ei ole (eli käyttäjä voi tykätä vain kerran)
		int i = 0;
		if (List1.size() == 0) {
			List1.add(username);
		} else {

			while (List1.size() > i) {
				if (List1.get(i).equals(username)) {
					break;
				} else {
					i++;
				}
				if (List1.size() == i) {
					List1.add(username);
				}
			}

		}

		Item.setLikers(List1);

		repository.save(Item);

		return "redirect:../itemlist";
	}

	// Lisää kirppistuote
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
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = "";
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		item.setUser(username);
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
