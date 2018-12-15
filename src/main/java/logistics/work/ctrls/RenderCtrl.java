package logistics.work.ctrls;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
public class RenderCtrl extends BaseCtrl {
    @RequestMapping(value="/work/employee/**", method=RequestMethod.GET)
    public ModelAndView employee(Map<String, Object> model, HttpServletRequest request) {
        return this.render("employee", null);
    }
    @RequestMapping(value="/work/leader/**", method=RequestMethod.GET)
    public ModelAndView leader(Map<String, Object> model, HttpServletRequest request) {
        return this.render("leader", null);
    }
    @RequestMapping(value="/work/personnel/**", method=RequestMethod.GET)
    public ModelAndView personnel(Map<String, Object> model, HttpServletRequest request) {
        return this.render("personnel", null);
    }
}
