import { Injectable } from '@angular/core';
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackbar: MatSnackBar) { }

  public showSnackBar(message: string): void {
    this.snackbar.open(message, 'SnackBar is here', {
      duration: 2000 // 2 second
    });
  }
}
