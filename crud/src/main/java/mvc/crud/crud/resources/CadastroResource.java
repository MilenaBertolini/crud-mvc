package mvc.crud.crud.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import mvc.crud.crud.dto.CadastroDTO;


@Controller
public class CadastroResource {
    
    @GetMapping("/cadastro")
    public String doGet(){
        return "cadastro";
    }

    private List<CadastroDTO> cadastro = new ArrayList<>();

    @PostMapping("cadastroPost")
    public String doPost(CadastroDTO dto, Model model){

        Long id = cadastro.size() + 1L;
        // cadastro.add(dto);
        cadastro.add(new CadastroDTO(id, dto.getInputNome(), dto.getInputEmail(), dto.getInputEndereco(), dto.getInputTelefone()));
        model.addAttribute("cadastro", cadastro);

        return "listagem";
    }

    @GetMapping("atualizaCadastro/{id}")
    public String doFind(@PathVariable("id") Long id, Model model){

        CadastroDTO encontrarCadastro = cadastro.stream().filter(dto -> id.equals(dto.getId())).findFirst().get();
        model.addAttribute("cadastroAtualizar", encontrarCadastro);

        return "atualizaCadastro";

    }

    @PostMapping("cadastroAtualizar")
    public String doUpdate(CadastroDTO dto, Model model){
        Long verificaId = dto.getId();
        
        // System.out.println(verificaId);
        if (verificaId > -1) {
            // Encontre o índice do registro existente com base no ID
            int index = -1;
            for (int i = 0; i < cadastro.size(); i++) {
                Long pegarId = cadastro.get(i).getId();
                // System.out.println(pegarId);
                if (pegarId == verificaId) {
                    index = i; 
                }
            }
            // Se o índice for diferente de -1, isso significa que um registro com o ID especificado foi encontrado
            if (index != -1) {
                // Atualiza o registro existente com os novos dados
                cadastro.set(index, dto);
            }
        } else {
            // Se o ID for 0, isso significa que é um novo registro, então adicione-o à lista
            cadastro.add(dto);
        }
    
        // Atualiza o cadastro
        model.addAttribute("cadastro", cadastro);
    
        return "listagem";

    }

    @PostMapping("deletaCadastro")
    public String doDelete(Long id, Model model){

        CadastroDTO encontrarCadastroDelete = cadastro.stream().filter(dto -> id.equals(dto.getId())).findFirst().get();
        cadastro.remove(encontrarCadastroDelete);

        model.addAttribute("cadastro", cadastro);

        return "listagem";

    }

}