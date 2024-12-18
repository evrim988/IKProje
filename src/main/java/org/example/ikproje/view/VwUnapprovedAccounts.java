package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VwUnapprovedAccounts {
	Long id;
	String firstName;
	String lastName;
	String phone;
	String email;
	String companyName;
}