package org.example.ikproje.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class VwUnapprovedCompany {
	Long id;
	String name;
	String phone;
	String email;
}