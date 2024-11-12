package br.com.dashboard.company.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/dashboard/company/menu/v1"])
@Tag(name = "Menu", description = "EndPoint For Managing All Menus")
class MenuController {

}
