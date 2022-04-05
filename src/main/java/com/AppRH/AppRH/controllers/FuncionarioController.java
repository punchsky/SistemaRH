package com.AppRH.AppRH.controllers;

import com.AppRH.AppRH.controllers.repository.DependentesRepository;
import com.AppRH.AppRH.controllers.repository.FuncionarioRepository;
import com.AppRH.AppRH.models.Dependentes;
import com.AppRH.AppRH.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class FuncionarioController {
    @Autowired
    private FuncionarioRepository fr;

    @Autowired
    private DependentesRepository dr;

    //cadastrar funcionários chama form
    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.GET)
    public String form() {

        return "funcionario/formFuncionario";
    }

    //cadastrar funcionários
    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
    public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/cadastrarFuncionario";
        }
        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionario cadastrado com sucesso!");
        return "redirect:/cadastrarFuncionario";
    }
 
    //listar funcionários
    @RequestMapping("/funcionarios")
    public ModelAndView listaFuncionarios() {
        ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
        Iterable<Funcionario> funcionarios = fr.findAll();
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }

    // listar dependentes
    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.GET)
    public ModelAndView dependentes(@PathVariable("id") long id){
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/dependentes");
        mv.addObject("funcionarios", funcionario);

        // lista de dependentes baseada no funcionário
        Iterable<Dependentes> dependentes = dr.findByFuncionario(funcionario);
        mv.addObject("dependentes", dependentes);
        return mv;
    }

    //adiciona dependentes

    @RequestMapping(value = "/dependentes/{id}", method = RequestMethod.POST)
    public String dependentesPost(@PathVariable("id") long id, Dependentes dependentes, BindingResult result, RedirectAttributes attributes){
              if(result.hasErrors()) {
                  attributes.addFlashAttribute("mensagem", "Verifique os campos!");
                  return "redirect:/dependentes/{id}";
              }
              if (dr.findByCpf(dependentes.getCpf()) != null){
                  attributes.addFlashAttribute("mensagem_erro","CPF Duplicado!" );
                  return "redirect:/dependentes/{id}";
              }
              Funcionario funcionario = fr.findById(id);
              dependentes.setFuncionario(funcionario);
              dr.save(dependentes);
              attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso");
              return "redirect:/dependentes/{id}";
    }

    //deleta funcionário
    @RequestMapping("/deletarFuncionario")
    public String deletarFuncionario(long id){
        Funcionario funcionario = fr.findById(id);
        fr.delete(funcionario);
        return "redirect:/funcionarios";
    }

    // Métodos que atualizam funcionários
    // form
    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.GET)
    public ModelAndView editarFuncionario(long id){
        Funcionario funcionario = fr.findById(id);
        ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
        mv.addObject("funcionario", funcionario); // mandando o objeto mv pra view
        return mv;
    }

    // update funcionário
    @RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
    public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes){
        fr.save(funcionario);
        attributes.addFlashAttribute("successs", "Funcionário alterado com sucesso!");

        long idLong = funcionario.getId();
        String id ="" + idLong;
        return "redirect:/dependentes/" +id;
    }
    //deletar dependente
    @RequestMapping("/deletarDependente")
    public String deletarDependente(String cpf){ // buscando o cpf recebido no parametro e deletando dependente pelo cpf
        Dependentes dependente = dr.findByCpf(cpf);

        Funcionario funcionario = dependente.getFuncionario();
        String codigo = "" + funcionario.getId(); //isso faz redirecionar pra pagina do funcionario após deletar o dependente

        dr.delete(dependente);

        return "redirect:/dependentes/" +codigo;
    }


}