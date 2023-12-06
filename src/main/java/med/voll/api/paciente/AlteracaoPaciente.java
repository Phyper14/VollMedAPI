package med.voll.api.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.CadastroEndereco;

public record AlteracaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        CadastroEndereco endereco
) {}
