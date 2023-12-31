package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.CadastroEndereco;

public record AlteracaoPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        CadastroEndereco endereco
) {}
