package dibimbing.team2.utils;

import org.springframework.stereotype.Component;

@Component("emailTemplate")
public class EmailTemplate {
    public String getRegisterTemplate() {
        return
        """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                    <style>
                                
                        body{\s
                            text-align: center;
                        }
                                
                        email-container {
                        padding-top: 10px;
                    }
                                
                        p {
                            text-align: center;
                            }
                        a.btn {
                        display: block;
                        margin: 30px auto;
                        background-color: #01c853;
                        padding: 10px 20px;
                        color: #fff;
                        text-decoration: none;
                        width: 30%;
                        text-align: center;
                        border: 1px solid #01c853;
                        text-transform: uppercase;
                        }
                                
                        a.btn:hover, a.btn:focus {
                        color: #01c853;
                        background-color: #fff;
                        border: 1px solid #01c853;
                        }
                                
                        .user-name {
                        text-transform: uppercase;
                        }
                        .manual-link,.manual-link:hover, .manual-link:focus {
                        display: block;
                        color: #396fad;
                        font-weight: bold;
                        margin-top: -15px;
                        }
                                
                        .mt--15 {
                        margin-top: -15px;
                        }
                       \s
                        </style>
                       \s
                </head>
                <body>
                    <div class=\\"email-container\\">
                        <p>Halo <span class=\\"user-name\\">{{USERNAME}}</span> Selamat bergabung di Team 2 Cell</p>
                        <p>Harap konfirmasikan email kamu dengan memasukan kode dibawah ini</p>
                        <br><br>
                        <p> kode: <b>{{VERIFY_TOKEN}}</b> </p>
                        <br><br>
                        <p class=\\"mt--15\\">Jika kamu butuh bantuan atau pertanyaan, hubungi customer care kami di (021)2736732376 atau kirim email ke team2dibimbing@gmail.com</p>
                        <p>Semoga harimu menyenangkan!</p>
                        <p>TEAM2 CELL,</p>
                        <p class=\\"mt--15\\".....</p></p>
                                
                    </div>
                   \s
                </body>
                </html>
                """;

    }

    public String getResetPassword(){
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Document</title>
                    <style>
                                
                        body{\s
                            text-align: center;
                        }
                                
                        email-container {
                        padding-top: 10px;
                    }
                                
                        p {
                            text-align: center;
                            }
                        a.btn {
                        display: block;
                        margin: 30px auto;
                        background-color: #01c853;
                        padding: 10px 20px;
                        color: #fff;
                        text-decoration: none;
                        width: 30%;
                        text-align: center;
                        border: 1px solid #01c853;
                        text-transform: uppercase;
                        }
                                
                        a.btn:hover, a.btn:focus {
                        color: #01c853;
                        background-color: #fff;
                        border: 1px solid #01c853;
                        }
                                
                        .user-name {
                        text-transform: uppercase;
                        }
                        .manual-link,.manual-link:hover, .manual-link:focus {
                        display: block;
                        color: #396fad;
                        font-weight: bold;
                        margin-top: -15px;
                        }
                                
                        .mt--15 {
                        margin-top: -15px;
                        }
                       \s
                        </style>
                       \s
                </head>
                <body>
                    <div class=\\"email-container\\">
                        <p>If you Requested a password for {{USERNAME}}, use the confirmation code below to complete the process. If you didn't make this request, ignore the email.</p>
                        <br><br>
                        <p>use this code to reset your password </p>
                        <p> {{PASS_TOKEN}} </p>
                        <br><br>
                        <p>Regards</p>
                       \s
                        <p>PT ABC</p>
                        <p class=\\"mt--15\\".....</p>
                        </div>
                   \s
                </body>
                </html>
                """;

    }

}
