package br.com.auladesenvolvimento.calculoimc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Locale;

import br.com.auladesenvolvimento.utils.ArmazenamentoIMC;
import br.com.auladesenvolvimento.utils.PessoaIMC;

public class MainActivity extends AppCompatActivity {

    private ViewHolder mainViewHolder = new ViewHolder();
    private ArmazenamentoIMC armazenamentoIMC;
    Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        this.mainViewHolder.imc = (TextView) findViewById(R.id.imc);
        this.mainViewHolder.classificacao = (TextView) findViewById(R.id.classificacao);
        this.mainViewHolder.nome = (EditText) findViewById(R.id.nome);
        this.mainViewHolder.peso = (EditText) findViewById(R.id.peso);
        this.mainViewHolder.altura = (EditText) findViewById(R.id.altura);
        this.mainViewHolder.calcular = (Button) findViewById(R.id.calcular);
        armazenamentoIMC = new ArmazenamentoIMC(this);


    }

    private class ViewHolder{
        TextView imc;
        TextView classificacao;
        EditText nome;
        EditText peso;
        EditText altura;
        Button calcular;
    }

    public void Calcular(View view){
        Double peso = Double.valueOf(this.mainViewHolder.peso.getText().toString());
        Double altura = Double.valueOf(this.mainViewHolder.altura.getText().toString());
        Double imc = calcularIMC(peso, altura);
        String classificacao = classificarIMC(imc);
        this.mainViewHolder.imc.setText(String.format(Locale.US, "%.2f", imc));
        this.mainViewHolder.classificacao.setText(classificacao);
    }

    public Double calcularIMC(Double peso, Double altura){
        return peso/(altura * altura);
    }

    public String classificarIMC(Double imc){
        if(imc < 17)
            return "Muito abaixo do Peso";
        if(imc < 18.49)
            return "Abaixo do Peso";
        if(imc < 24.99)
            return "Peso Normal";
        if(imc < 29.99)
            return "Acima do Peso";
        if(imc < 34.99)
            return "Obesidade I";
        if(imc < 39.99)
            return "Obsesidade II - Severa";
        return "Obesidade III - Morbida";
    }

    public void Salvar(View view){
        Double peso = Double.valueOf(this.mainViewHolder.peso.getText().toString());
        Double altura = Double.valueOf(this.mainViewHolder.altura.getText().toString());
        Double imc = Double.valueOf(this.mainViewHolder.imc.getText().toString());
        String classificacao = this.mainViewHolder.classificacao.getText().toString();
        String nome = this.mainViewHolder.nome.getText().toString();
        PessoaIMC pessoaIMC = new PessoaIMC(nome, peso, altura, imc, classificacao);
        String jsonPessoaIMC = gson.toJson(pessoaIMC);
        armazenamentoIMC.salvar(nome,jsonPessoaIMC);
        this.mainViewHolder.peso.setText("");
        this.mainViewHolder.altura.setText("");
        this.mainViewHolder.imc.setText("");
        this.mainViewHolder.classificacao.setText("");
        this.mainViewHolder.nome.setText("");
    }

    public void Listar(View view){
        Intent chamarListagem = new Intent(this, ListagemActivity.class);
        startActivity(chamarListagem);
    }
}

