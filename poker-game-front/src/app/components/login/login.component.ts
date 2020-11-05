import {Component, OnInit} from '@angular/core';
import {UserService} from '../../services/user.service';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;
  password: string;
  loginFailed = false;

  constructor(private userService: UserService, private dialogRef: MatDialogRef<LoginComponent>) {
  }

  ngOnInit(): void {
  }

  authenticate(): void {
    this.loginFailed = false;
    this.userService.login(this.username, this.password).subscribe(result => {
      this.userService.saveUserToken(result.value);
      this.dialogRef.close(result);
    }, error => {
      this.loginFailed = true;
    });
  }
}
