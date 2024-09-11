package com.example.demo;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {
    static Image image = new Image();
    @RequestMapping("/image")
    public String image(Model model){
        model.addAttribute("image", image.encodeToString());
        return "image";
    }
}
