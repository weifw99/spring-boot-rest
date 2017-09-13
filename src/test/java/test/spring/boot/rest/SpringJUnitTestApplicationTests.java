package test.spring.boot.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.boot.rest.Application;
import spring.boot.rest.domain.User;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//这是JUnit的注解，通过这个注解让SpringJUnit4ClassRunner这个类提供Spring测试上下文。
@RunWith(SpringJUnit4ClassRunner.class)  
//这是Spring Boot注解，为了进行集成测试，需要通过这个注解加载和配置Spring应用上下  
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration  
public class SpringJUnitTestApplicationTests {  
  
    @Autowired  
    private WebApplicationContext context;  
      
    private MockMvc mockMvc;   
  
    @Before   
    public void setupMockMvc() throws Exception {   
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();   
    }  
      
      
    /*** 
     * 测试添加用户接口 
     * @throws Exception 
     */  
    @Test  
    public void testAddUser() throws Exception  
    {  
        //构造添加的用户信息  
        User userInfo = new User();
        userInfo.setName("testuser2");  
        userInfo.setAge(29);  
        userInfo.setAddress("北京");  
        ObjectMapper mapper = new ObjectMapper();  
          
        //调用接口，传入添加的用户参数  
        mockMvc.perform(post("/user/adduser")  
                .contentType(MediaType.APPLICATION_JSON_UTF8)  
                .content(mapper.writeValueAsString(userInfo)))  
        //判断返回值，是否达到预期，测试示例中的返回值的结构如下{"errcode":0,"errmsg":"OK","p2pdata":null}  
        .andExpect(status().isOk())  
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
        //使用jsonPath解析返回值，判断具体的内容  
        .andExpect(jsonPath("$.errcode", is(0)))  
        .andExpect(jsonPath("$.p2pdata", notNullValue()))  
        .andExpect(jsonPath("$.p2pdata.id", not(0)))  
        .andExpect(jsonPath("$.p2pdata.name", is("testuser2")));  
    }  
      
    /*** 
     * 测试更新用户信息接口 
     * @throws Exception 
     */  
    @Test  
    public void testUpdateUser() throws Exception  
    {  
        //构造添加的用户信息，更新id为2的用户的用户信息  
        User userInfo = new User();
        userInfo.setId((long)2);  
        userInfo.setName("testuser");  
        userInfo.setAge(26);  
        userInfo.setAddress("南京");  
        ObjectMapper mapper = new ObjectMapper();  
          
        mockMvc.perform(put("/user/updateuser")  
                .contentType(MediaType.APPLICATION_JSON_UTF8)  
                .content(mapper.writeValueAsString(userInfo)))  
        //判断返回值，是否达到预期，测试示例中的返回值的结构如下  
        //{"errcode":0,"errmsg":"OK","p2pdata":null}  
        .andExpect(status().isOk())  
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
        .andExpect(jsonPath("$.errcode", is(0)))  
        .andExpect(jsonPath("$.p2pdata", notNullValue()))  
        .andExpect(jsonPath("$.p2pdata.id", is(2)))  
        .andExpect(jsonPath("$.p2pdata.name", is("testuser")))  
        .andExpect(jsonPath("$.p2pdata.age", is(26)))  
        .andExpect(jsonPath("$.p2pdata.address", is("南京")));  
    }  
      
    /*** 
     * 测试根据用户id获取用户信息接口 
     * @throws Exception 
     */  
    @Test  
    public void testGetUser() throws Exception  
    {
        ResultActions actions = mockMvc.perform(get("/user/get.json?id=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", is("200")))
                .andExpect(jsonPath("$.message", is("成功")))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(2)));
        /*.andExpect(jsonPath("$.p2pdata.name", is("testuser")))
        .andExpect(jsonPath("$.p2pdata.age", is(26)))  
        .andExpect(jsonPath("$.p2pdata.address", is("南京")));  */

        MvcResult result = actions.andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }  
  
    /*** 
     * 测试获取用户列表接口 
     * @throws Exception 
     */  
    @Test  
    public void testGetUsers() throws Exception  
    {  
        mockMvc.perform(get("/user/getalluser"))  
        .andExpect(status().isOk())  
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))  
        .andExpect(jsonPath("$.errcode", is(0)))  
        .andExpect(jsonPath("$.p2pdata", notNullValue()));  
    }  
}  