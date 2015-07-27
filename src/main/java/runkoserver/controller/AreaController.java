package runkoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import runkoserver.domain.Area;
import runkoserver.service.AreaService;

@Controller
@RequestMapping("/content")
public class AreaController {

    @Autowired
    AreaService areaService;

    @RequestMapping(value = "/{areaid}", method = RequestMethod.GET)
    public String getArea(@PathVariable Long areaid, Model model) {
        model.addAttribute("area", areaService.findById(areaid));
        
        return "";
    }
    
    @RequestMapping(value = "/areaform", method = RequestMethod.GET)
    public String areaForm() {
        return "/content/area_form";
    }

    @RequestMapping(value = "/areaform", method = RequestMethod.POST)
    public String postAreaContent(RedirectAttributes redirectAttributes,
            @ModelAttribute Area areaContent) {
        if (areaService.save(areaContent)) {
            redirectAttributes.addFlashAttribute("message", "Uusi alue tallennettu!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Alueen tallentaminen epäonnistui");
        }
        return "redirect:/";
    }
}
