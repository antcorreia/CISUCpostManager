package com.company;

import java.io.*;
import java.util.*;

/***
 * Classe com todas as funcoes principais do programa.
 */
class Projeto{
    private ArrayList<Investigador> investigadores;
    private ArrayList<Grupo> grupos;
    private ArrayList<Publicacao> publicacoes;

    public Projeto(){
        this.investigadores = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.publicacoes = new ArrayList<>();

        if (!receber_dados(investigadores, grupos, publicacoes)) {
            inicializar_grupos(grupos);
            receber_investigadores(investigadores, grupos);
            receber_responsaveis(investigadores, grupos);
            receber_publicacoes(investigadores, publicacoes);
        }
        menu(investigadores, grupos, publicacoes);
    }

    /***
     * Menu usado no programa.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void menu(ArrayList<Investigador> investigadores, ArrayList<Grupo> grupos, ArrayList<Publicacao> publicacoes) {
        while (true) {
            System.out.print("" +
                    "[0] -> Fechar a aplicação.\n" +
                    "[1] -> Apresentar o número de investigadores.\n" +
                    "[2] -> Apresentar o número de estudantes e de membros efetivos.\n" +
                    "[3] -> Apresentar o número de publicações dos últimos 5 anos.\n" +
                    "[4] -> Apresentar o número de publicações de cada tipo.\n" +
                    "[5] -> Listar as publicações de um grupo de investigação.\n" +
                    "[6] -> Listar os membros de um grupo de investigação.\n" +
                    "[7] -> Listar as publicações de um investigador.\n" +
                    "[8] -> Listar todos os grupos de investigação.\n" +
                    "\n" +
                    "Escolha uma opção: ");

            try {
                Scanner scanner = new Scanner(System.in);
                int n = scanner.nextInt();
                System.out.print("\n");

                if (n == 0) {
                    guardar_dados(investigadores, grupos, publicacoes);
                    return;
                } else if (n == 1)
                    contar_investigadores(investigadores);
                else if (n == 2)
                    contar_investigadores2(investigadores);
                else if (n == 3)
                    contar_publicacoes(publicacoes);
                else if (n == 4)
                    contar_publicacoes2(publicacoes);
                else if (n == 5)
                    listar_publicacoes_grupo(grupos, publicacoes);
                else if (n == 6)
                    membros_grupo(grupos);
                else if (n == 7)
                    listar_publicacoes_membro(investigadores, publicacoes);
                else if (n == 8)
                    listar_grupos(grupos, publicacoes);
                else
                    System.out.print("Essa opção não é válida.\n\n");
            }
            catch(InputMismatchException e){
                System.out.print("\nEssa opção não é válida.\n\n");
            }
        }
    }

    /***
     * Tenta receber os dados de um ficheiro de objetos.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     * @return Devolve true se conseguir receber os dados, false se nao conseguir.
     */
    public static boolean receber_dados(ArrayList<Investigador> investigadores, ArrayList<Grupo> grupos, ArrayList<Publicacao> publicacoes){
        try {
            FileInputStream readData = new FileInputStream("investigadores.obj");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            Investigador obj;
            while ((obj = (Investigador)readStream.readObject()) != null){
                investigadores.add(obj);
            }
            readStream.close();
            readData.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiros de objetos não encontrados. Dados carregados a partir dos ficheiros de texto.\n");
            return false;
        } catch (Exception ignored){}

        try {
            FileInputStream readData = new FileInputStream("grupos.obj");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            Grupo obj;
            while ((obj = (Grupo)readStream.readObject()) != null){
                grupos.add(obj);
            }
            readStream.close();
            readData.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiros de objetos não encontrados. Dados carregados a partir dos ficheiros de texto.\n");
            return false;
        } catch (Exception ignored){}

        try {
            FileInputStream readData = new FileInputStream("publicacoes.obj");
            ObjectInputStream readStream = new ObjectInputStream(readData);

            Publicacao obj;
            while ((obj = (Publicacao)readStream.readObject()) != null){
                publicacoes.add(obj);
            }
            readStream.close();
            readData.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiros de objetos não encontrados. Dados carregados a partir dos ficheiros de texto.\n");
            return false;
        } catch (Exception ignored){}

        return true;
    }

    /***
     * Preenche o ArrayList dos grupos com as informacoes default dos 6 grupos do CISUC.
     * @param grupos ArrayList onde estão guardados os grupos.
     */
    public static void inicializar_grupos (ArrayList < Grupo > grupos) {
        grupos.add(new Grupo("Adaptive Computation", "AC", null, new ArrayList<>()));
        grupos.add(new Grupo("Cognitive and Media Systems", "CMS", null, new ArrayList<>()));
        grupos.add(new Grupo("Evolutionary and Complex Systems", "ECOS", null, new ArrayList<>()));
        grupos.add(new Grupo("Information Systems", "IS", null, new ArrayList<>()));
        grupos.add(new Grupo("Communications and Telematics", "CT", null, new ArrayList<>()));
        grupos.add(new Grupo("Software and Systems Engineering", "SSE", null, new ArrayList<>()));
    }

    /***
     * Lê o ficheiro de dados que tem as informacoes sobre os investigadores e preenche o ArrayList correspondente.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param grupos ArrayList onde estão guardados os grupos.
     */
    public static void receber_investigadores (ArrayList < Investigador > investigadores, ArrayList < Grupo > grupos) {
        try {
            File f_investigadores = new File("investigadores.txt");
            Scanner scanner = new Scanner(f_investigadores);

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (dados[0].equals("Estudantes:"))
                    break;
                if (!dados[0].equals("Membros Efetivos:") && !dados[0].equals("")) {
                    MembroEfetivo investigador = new MembroEfetivo(dados[0], dados[1], getGrupo(grupos, dados[2]), Integer.parseInt(dados[3]), Integer.parseInt(dados[4]));
                    investigadores.add(investigador);
                    grupos.get(grupos.indexOf(getGrupo(grupos, dados[2]))).adicionarInvestigador(investigador);
                }
            }

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                Data data = new Data(Integer.parseInt(dados[4].split("/")[0]), Integer.parseInt(dados[4].split("/")[1]), Integer.parseInt(dados[4].split("/")[2]));
                Estudante investigador = new Estudante(dados[0], dados[1], getGrupo(grupos, dados[2]), dados[3], data, getOrientador(investigadores, Integer.parseInt(dados[5])));
                investigadores.add(investigador);
                grupos.get(grupos.indexOf(getGrupo(grupos, dados[2]))).adicionarInvestigador(investigador);
            }
        }
        catch (FileNotFoundException e) {
            System.out.print("Não foram encontrados todos os ficheiros de texto necessários.");
            System.exit(0);
        }
        catch (Exception e) {
            System.out.print("Ficheiro dos investigadores mal configurado.");
            System.exit(0);
        }
    }

    /***
     * Lê o ficheiro de dados que tem os numeros dos responsaveis por cada grupo do CISUC e altera o ArrayList dos grupos.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param grupos ArrayList onde estão guardados os grupos.
     */
    public static void receber_responsaveis (ArrayList < Investigador > investigadores, ArrayList < Grupo > grupos){
        try {
            File f_responsaveis = new File("responsaveis.txt");
            Scanner scanner = new Scanner(f_responsaveis);

            while (scanner.hasNextLine()) {
                String string = scanner.nextLine().split(" -> ")[0];
                int numero = Integer.parseInt(scanner.nextLine().split(" -> ")[1]);

                grupos.get(grupos.indexOf(getGrupo(grupos, string))).setResponsavel(getOrientador(investigadores, numero));
            }
        } catch (FileNotFoundException e) {
            System.out.print("Não foram encontrados todos os ficheiros de texto necessários.");
            System.exit(0);
        }
        catch (Exception e) {
            System.out.print("Ficheiro dos responsáveis mal configurado.");
            System.exit(0);
        }
    }

    /***
     * Lê o ficheiro de dados que tem as informacoes sobre as publicacoes e preenche o ArrayList correspondente.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param publicacoes ArrayList onde estão guardados as publicacoes.
     */
    public static void receber_publicacoes(ArrayList < Investigador > investigadores, ArrayList < Publicacao > publicacoes){
        try {
            File f_publicacoes = new File("publicacoes.txt");
            Scanner scanner = new Scanner(f_publicacoes);

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (dados[0].equals("Revistas:"))
                    break;
                if (!dados[0].equals("Conferencias:") && !dados[0].equals("")) {
                    Conferencia conferencia = new Conferencia(new ArrayList<>(), dados[0], new ArrayList<>(), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), null, null, null, null);
                    dados = scanner.nextLine().split(", ");
                    conferencia.setPalavrasChave(dados);
                    dados = scanner.nextLine().split(", ");
                    for (String s : dados)
                        conferencia.adicionarAutor(getInvestigador(investigadores, s));
                    conferencia.setResumo(scanner.nextLine());
                    dados = scanner.nextLine().split(", ");

                    conferencia.setNome(dados[0]);
                    Data data = new Data(Integer.parseInt(dados[1].split("/")[0]), Integer.parseInt(dados[1].split("/")[1]), Integer.parseInt(dados[1].split("/")[2]));
                    conferencia.setData(data);
                    conferencia.setLocalizacao(dados[2]);

                    publicacoes.add(conferencia);
                }
            }

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (dados[0].equals("Livros:"))
                    break;
                if (!dados[0].equals("")) {
                    Revista revista = new Revista(new ArrayList<>(), dados[0], new ArrayList<>(), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), null, null, null, 0);
                    dados = scanner.nextLine().split(", ");
                    revista.setPalavrasChave(dados);
                    dados = scanner.nextLine().split(", ");
                    for (String s : dados)
                        revista.adicionarAutor(getInvestigador(investigadores, s));
                    revista.setResumo(scanner.nextLine());
                    dados = scanner.nextLine().split(", ");

                    revista.setNome(dados[0]);
                    Data data = new Data(Integer.parseInt(dados[1].split("/")[0]), Integer.parseInt(dados[1].split("/")[1]), Integer.parseInt(dados[1].split("/")[2]));
                    revista.setData(data);
                    revista.setNumero(Integer.parseInt(dados[2]));

                    publicacoes.add(revista);
                }
            }

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (dados[0].equals("Capitulos:"))
                    break;
                if (!dados[0].equals("")) {
                    Livro livro = new Livro(new ArrayList<>(), dados[0], new ArrayList<>(), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), null, null, null);
                    dados = scanner.nextLine().split(", ");
                    livro.setPalavrasChave(dados);
                    dados = scanner.nextLine().split(", ");
                    for (String s : dados)
                        livro.adicionarAutor(getInvestigador(investigadores, s));
                    livro.setResumo(scanner.nextLine());
                    dados = scanner.nextLine().split(", ");

                    livro.setEditora(dados[0]);
                    livro.setISBN(dados[1]);

                    publicacoes.add(livro);
                }
            }

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (dados[0].equals("LivrosConferencias:"))
                    break;
                if (!dados[0].equals("")) {
                    Capitulo capitulo = new Capitulo(new ArrayList<>(), dados[0], new ArrayList<>(), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), null, null, null, null, 0, 0);
                    dados = scanner.nextLine().split(", ");
                    capitulo.setPalavrasChave(dados);
                    dados = scanner.nextLine().split(", ");
                    for (String s : dados)
                        capitulo.adicionarAutor(getInvestigador(investigadores, s));
                    capitulo.setResumo(scanner.nextLine());
                    dados = scanner.nextLine().split(", ");

                    capitulo.setEditora(dados[0]);
                    capitulo.setISBN(dados[1]);
                    dados = scanner.nextLine().split(", ");
                    capitulo.setNome(dados[0]);
                    capitulo.setPaginaInicio(Integer.parseInt(dados[1]));
                    capitulo.setPaginaFinal(Integer.parseInt(dados[2]));

                    publicacoes.add(capitulo);
                }
            }

            while (scanner.hasNextLine()) {
                String[] dados = scanner.nextLine().split(", ");
                if (!dados[0].equals("")) {
                    LivroConferencia livroConferencia = new LivroConferencia(new ArrayList<>(), dados[0], new ArrayList<>(), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]), null, null, null, null, 0);
                    dados = scanner.nextLine().split(", ");
                    livroConferencia.setPalavrasChave(dados);
                    dados = scanner.nextLine().split(", ");
                    for (String s : dados)
                        livroConferencia.adicionarAutor(getInvestigador(investigadores, s));
                    livroConferencia.setResumo(scanner.nextLine());
                    dados = scanner.nextLine().split(", ");

                    livroConferencia.setEditora(dados[0]);
                    livroConferencia.setISBN(dados[1]);
                    dados = scanner.nextLine().split(", ");
                    livroConferencia.setNome(dados[0]);
                    livroConferencia.setArtigos(Integer.parseInt(dados[1]));
                    publicacoes.add(livroConferencia);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.print("Não foram encontrados todos os ficheiros de texto necessários.");
            System.exit(0);
        }
        catch (Exception e) {
            System.out.print("Ficheiro das publicações mal configurado.");
            System.exit(0);
        }
    }

    /***
     * Conta o numero total de membros.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     */
    public static void contar_investigadores (ArrayList < Investigador > investigadores) {
        int count = 0;

        for (Investigador ignored : investigadores)
            count++;

        System.out.printf("Há atualmente %d investigadores na base de dados.\n\n", count);
    }

    /***
     * Conta o numero de investigadores de cada tipo.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     */
    public static void contar_investigadores2 (ArrayList < Investigador > investigadores) {
        int count1 = 0;
        int count2 = 0;

        for (Investigador investigador : investigadores) {
            if (investigador.classe().equals("estudante"))
                count1++;
            else
                count2++;
        }

        System.out.printf("Há atualmente %d Estudantes e %d Membros Efetivos na base de dados.\n\n", count1, count2);
    }

    /***
     * Conta o numero de publicacoes dos ultimos 5 anos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void contar_publicacoes (ArrayList < Publicacao > publicacoes) {
        int count = 0;

        for (Publicacao publicacao : publicacoes)
            if (publicacao.getAno() > 2015)
                count++;

        System.out.printf("Há atualmente %d publicações dos últimos 5 anos na base de dados.\n\n", count);
    }

    /***
     * Apresenta o numero de publicacoes de cada tipo, organizadas e agrupadas por certos parametros.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void contar_publicacoes2 (ArrayList < Publicacao > publicacoes) {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;

        for (Publicacao publicacao : publicacoes) {
            switch (publicacao.classe()) {
                case "conferencia" -> count1++;
                case "revista" -> count2++;
                case "capitulo" -> {
                    count3++;
                    count4++;
                }
                case "livroconferencia" -> {
                    count3++;
                    count5++;
                }
                case "livro" -> count3++;
            }
        }

        System.out.printf("Há atualmente %d publicações sobre conferências, %d sobre revistas e %d sobre livros, sendo destas %d sobre capítulos e %d sobre livros de conferências.\n\n", count1, count2, count3, count4, count5);
    }

    /***
     * Lista todas as publicacoes de um grupo organizadas por certos parametros.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void listar_publicacoes_grupo(ArrayList<Grupo> grupos, ArrayList<Publicacao> publicacoes){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual o nome/acronimo do grupo? ");
        String string = scanner.nextLine();
        Grupo grupo = getGrupo(grupos, string);
        System.out.print("\n");

        if (grupo == null) {
            System.out.println("Esse grupo não existe.\n");
            return;
        }

        publicacoes.sort(Comparator.comparing(Publicacao::impacto));
        publicacoes.sort(Comparator.comparing(Publicacao::classe));
        publicacoes.sort(Comparator.comparing(Publicacao::getAno));

        for (Publicacao publicacao : publicacoes)
            for (Investigador investigador : publicacao.getAutores())
                if (investigador.getGrupo().getNome().equals(grupo.getNome())){
                    System.out.println(publicacao);
                    break;
                }
        System.out.print("\n");
    }

    /***
     * Lista todas as publicacoes de um membro organizadas por certos parametros.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void listar_publicacoes_membro(ArrayList<Investigador> investigadores, ArrayList<Publicacao> publicacoes){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual o email do membro? ");
        String string = scanner.nextLine();
        Investigador investigador = getInvestigador(investigadores, string);
        System.out.print("\n");

        if (investigador == null) {
            System.out.println("Esse membro não existe.\n");
            return;
        }

        publicacoes.sort(Comparator.comparing(Publicacao::impacto));
        publicacoes.sort(Comparator.comparing(Publicacao::classe));
        publicacoes.sort(Comparator.comparing(Publicacao::getAno));

        for (Publicacao publicacao : publicacoes)
            for (Investigador i : publicacao.getAutores())
                if (investigador.getEmail().equals(i.getEmail())){
                    System.out.println(publicacao);
                    break;
                }
        System.out.print("\n");
    }

    /***
     * Lista todos os grupos de investigacao e algumas informacoes sobre eles.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void listar_grupos(ArrayList<Grupo> grupos, ArrayList<Publicacao> publicacoes){
        for (Grupo grupo : grupos){
            System.out.println(grupo.getNome() + " - " + grupo.getAcronimo());
            String[] tipos = {"conferencia", "revista", "livro", "capitulo", "livroconferencia"};
            String[] impactos = {"A", "B", "C"};
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            int count4 = 0;

            for (Investigador investigador : grupo.getMembros()) {
                if (investigador.classe().equals("estudante"))
                    count1++;
                else
                    count2++;
            }
            System.out.printf("Total de Membros: %d\nEstudantes: %d\nMembros Efetivos: %d\n", count1 + count2, count1, count2);
            for (Publicacao publicacao : publicacoes)
                if (publicacao.getAno() > 2015)
                    for (Investigador investigador : publicacao.getAutores())
                        if (investigador.getGrupo().getNome().equals(grupo.getNome())){
                            count3++;
                            break;
                        }
            System.out.printf("Número de publicações dos últimos 5 anos: %d\n", count3);
            for (int i = 2015; i <= 2021; i++){
                System.out.println(i + ":");
                for (String tipo : tipos) {
                    switch (tipo) {
                        case "conferencia" -> System.out.print("  Conferência:\n");
                        case "revista" -> System.out.print("  Revista:\n");
                        case "livro" -> System.out.print("  Livro:\n");
                        case "capitulo" -> System.out.print("  Capítulo:\n");
                        case "livroconferencia" -> System.out.print("  Livro de Conferência:\n");
                    }
                    for (String impacto : impactos) {
                        System.out.println("    Impacto " + impacto + ":");
                        for (Publicacao publicacao : publicacoes)
                            if (publicacao.getAno() == i && publicacao.classe().equals(tipo) && publicacao.impacto().equals(impacto))
                                count4++;

                        System.out.printf("      %d publicações\n", count4);
                        count4 = 0;
                    }
                }
            }
        }
        System.out.print("\n");
    }

    /***
     * Guarda os dados dos ArrayList's nos ficheiros de objetos.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param publicacoes ArrayList onde estão guardadas as publicacoes.
     */
    public static void guardar_dados (ArrayList < Investigador > investigadores, ArrayList < Grupo > grupos, ArrayList < Publicacao > publicacoes){
        try {
            FileOutputStream writeData = new FileOutputStream("investigadores.obj");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            for (Investigador i : investigadores) {
                writeStream.writeObject(i);
                writeStream.flush();
            }
            writeStream.close();
        } catch (Exception e) {
            System.out.print("Erro a guardar os dados dos investigadores.");
        }

        try {
            FileOutputStream writeData = new FileOutputStream("grupos.obj");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            for (Grupo g : grupos) {
                writeStream.writeObject(g);
                writeStream.flush();
            }
            writeStream.close();
        } catch (Exception e) {
            System.out.print("Erro a guardar os dados dos grupos.");
        }

        try {
            FileOutputStream writeData = new FileOutputStream("publicacoes.obj");
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            for (Publicacao p : publicacoes) {
                writeStream.writeObject(p);
                writeStream.flush();
            }
            writeStream.close();
        } catch (Exception e) {
            System.out.print("Erro a guardar os dados das publicacoes.");
        }
    }

    /***
     * Lista os membros de um grupo de investigacao.
     * @param grupos ArrayList onde estão guardados os grupos.
     */
    public static void membros_grupo (ArrayList < Grupo > grupos) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Qual o nome/acronimo do grupo? ");
        String string = scanner.nextLine();
        System.out.print("\n");

        for (Grupo grupo : grupos) {
            if (string.equals(grupo.getNome()) || string.equals(grupo.getAcronimo())) {
                System.out.println(grupo.getNome());
                System.out.println("Membros Efetivos:");
                for (Investigador investigador : grupo.getMembros())
                    if (investigador.classe().equals("membroefetivo"))
                        System.out.println(investigador.getNome());
                System.out.println("Estudantes:");
                for (Investigador investigador : grupo.getMembros())
                    if (investigador.classe().equals("estudante"))
                        System.out.println(investigador.getNome());
                System.out.print("\n");
                return;
            }
        }

        System.out.println("Esse grupo não existe.\n");
    }

    /***
     * Procura por um grupo no ArrayList dos mesmos.
     * @param grupos ArrayList onde estão guardados os grupos.
     * @param string Sigla/nome do grupo pretendido.
     * @return Devolve um ponteiro para o grupo, null se este nao estiver na lista.
     */
    public static Grupo getGrupo (ArrayList < Grupo > grupos, String string){
        return switch (string) {
            case "AC", "Adaptive Computation" -> grupos.get(0);
            case "CMS", "Cognitive and Media Systems" -> grupos.get(1);
            case "ECOS", "Evolutionary and Complex Systems" -> grupos.get(2);
            case "IS", "Information Systems" -> grupos.get(3);
            case "LCT", "Communications and Telematics" -> grupos.get(4);
            case "SSE", "Software and Systems Engineering" -> grupos.get(5);
            default -> null;
        };

    }

    /***
     * Procura por um Membro Efetivo no ArrayList de investigadores.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param telefone Numero de telefone do membro efetivo pretendido.
     * @return Devolve um ponteiro para o membro efetivo, null se este nao estiver na lista.
     */
    public static MembroEfetivo getOrientador (ArrayList < Investigador > investigadores,int telefone){
        for (Investigador membro : investigadores) {
            if (membro.classe().equals("membroefetivo"))
                if (((MembroEfetivo) membro).getTelefone() == telefone)
                    return (MembroEfetivo) membro;
        }

        return null;
    }

    /***
     * Procura por um investigador no ArrayList dos mesmos.
     * @param investigadores ArrayList onde estão guardados os investigadores.
     * @param email Email do investigador pretendido.
     * @return Devolve um ponteiro para o investigador, null se este nao estiver na lista.
     */
    public static Investigador getInvestigador (ArrayList < Investigador > investigadores, String email){
        for (Investigador investigador : investigadores)
            if (email.equals(investigador.getEmail()))
                return investigador;

        return null;
    }
}
