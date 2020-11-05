import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {SubscribeComponent} from '../modals/subscribe/subscribe.component';
import {UserService} from '../../services/user.service';
import {LoginComponent} from '../login/login.component';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  isUserAuthenticated = false;

  constructor(private dialog: MatDialog, private userService: UserService) { }

  ngOnInit(): void {
    this.isUserAuthenticated = this.userService.isPlayerAuthenticated();
  }

  openCreateAccountDialog(): void {
      const dialogRef = this.dialog.open(SubscribeComponent);
      dialogRef.afterClosed().subscribe(() => {
        this.isUserAuthenticated = this.userService.isPlayerAuthenticated();
      });
  }

  openLoginDialog(): void {
    const dialogRef = this.dialog.open(LoginComponent);
    dialogRef.afterClosed().subscribe(() => {
      this.isUserAuthenticated = this.userService.isPlayerAuthenticated();
    });
  }
}
