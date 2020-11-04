import { Component, OnInit } from '@angular/core';
import {UserSubscription} from '../../../dtos/user-subscription';
import {UserService} from '../../../services/user.service';
import {MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-subscribe',
  templateUrl: './subscribe.component.html',
  styleUrls: ['./subscribe.component.scss']
})
export class SubscribeComponent implements OnInit {
  user: UserSubscription = new UserSubscription();
  usernameUsed = false;
  emailUsed = false;

  constructor(private userService: UserService, private dialogRef: MatDialogRef<SubscribeComponent>) { }

  ngOnInit(): void {
  }

  onFormSubmit(): void {
    this.userService.createPlayerAccount(this.user).subscribe(token => {
      this.userService.saveUserToken(token.value);
      this.dialogRef.close();
    });
  }

  checkUsernameAvailability(): void {
    this.userService.isUsernameAvailable(this.user.username).subscribe(result => {
      this.usernameUsed = !result.value;
    });
  }

  verifyEmailAvailability(): void {
    this.userService.isEmailAvailable(this.user.email).subscribe(result => {
      this.emailUsed = !result.value;
    });
  }
}
