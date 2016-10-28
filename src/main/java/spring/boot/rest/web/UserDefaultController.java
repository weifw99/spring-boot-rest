package spring.boot.rest.web;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.boot.rest.common.RestResult;
import spring.boot.rest.domain.User;
import spring.boot.rest.service.UserService;

/**
 * 默认没有添加Swagger2注解, 默认生成的restful api
 */
@RestController
@RequestMapping(path = "/d/user")
public class UserDefaultController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public RestResult getUser(@RequestParam Long id) {
        User user = userService.getUser(id);
        return new RestResult(user);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public RestResult getUser1(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        return new RestResult(user);
    }

    /**
     * post 使用@RequestBody注解
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestResult add(@RequestBody User user) {
        userService.saveUser(user);
        return new RestResult(user);
    }

    /**
     * post 使用@RequestBody注解
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RestResult save(@RequestBody User user) {
        userService.saveUser(user);
        return new RestResult(user);
    }

    /**
     * post使用@ModelAttribute注解
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public RestResult save1(@ModelAttribute User user) {
        userService.saveUser(user);
        return new RestResult(user);
    }

    /**
     * post 使用@RequestParam 注解
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public RestResult deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return new RestResult(id);
    }

    /**
     * 使用put修改
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public RestResult updateUser(@PathVariable("id") long id, @RequestBody User user) {
        userService.editUser(user);
        return new RestResult(id);
    }

    /**
     * 使用delete请求修改
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public RestResult deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return new RestResult(id);
    }
}